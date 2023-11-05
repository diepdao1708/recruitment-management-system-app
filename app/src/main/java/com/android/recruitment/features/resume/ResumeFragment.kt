package com.android.recruitment.features.resume

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentResumeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ResumeFragment : Fragment() {
    private lateinit var binding: FragmentResumeBinding
    private val viewModel: ResumeViewModel by viewModels()
    private val resumeAdapter: ResumeAdapter by lazy {
        ResumeAdapter(listener = object : ResumeAdapter.OnClickListener {
            override fun onItemClick(resume: ItemResume) {
                viewModel.navigationToDetail(resume)
            }
        })
    }
    private var isAllFabVisible = false

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                if (uri != null) {
                    viewModel.navigateToDetail(uri)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResumeBinding.inflate(inflater, container, false)
        if (!checkStoragePermissions()) {
            requestPermission()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabCreate.shrink()

        binding.rcvResume.adapter = resumeAdapter
        onClick()
        observer()
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                resumeAdapter.updateData(uiState.resumeList)
            }
        }
        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is ResumeViewModel.Event.NavigateToDetail -> {
                    findNavController().navigate(
                        R.id.action_resumeFragment_to_resumeDetailFragment,
                        it.bundle,
                    )
                }

                else -> {
                    // noop
                }
            }
        }
    }

    private fun onClick() {
        binding.fabCreate.setOnClickListener {
            binding.tvNewCV.isVisible = !isAllFabVisible
            binding.tvUploadCV.isVisible = !isAllFabVisible
            isAllFabVisible = if (!isAllFabVisible) {
                binding.fabUploadCV.show()
                binding.fabNewCV.show()
                binding.fabCreate.extend()
                true
            } else {
                binding.fabUploadCV.hide()
                binding.fabNewCV.hide()
                binding.fabCreate.shrink()
                false
            }
        }
        binding.fabNewCV.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            resultLauncher.launch(intent)
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            startActivity(
                Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse(String.format("package:%s", context?.packageName))
                )
            )
        }
    }

    fun checkStoragePermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //Android is 11 (R) or above
            Environment.isExternalStorageManager()
        } else {
            //Below android 11
            val write =
                context?.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                }
            val read =
                context?.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                }
            read == PackageManager.PERMISSION_GRANTED && write == PackageManager.PERMISSION_GRANTED
        }
    }
}