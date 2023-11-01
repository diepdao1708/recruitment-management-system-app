package com.android.recruitment.features.resume

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.recruitment.databinding.FragmentResumeBinding
import com.android.recruitment.utils.DocumentUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeFragment : Fragment() {
    private lateinit var binding: FragmentResumeBinding
    private val viewModel: ResumeViewModel by viewModels()
    private var isAllFabVisible = false

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                if (uri != null) {
                    val file = DocumentUtils.getFile(activity, uri)
                    viewModel.uploadResume(file)
                }
                binding.pdfView.fromUri(uri)
                    .defaultPage(0)
                    .spacing(10)
                    .load()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResumeBinding.inflate(inflater, container, false)
        requestPermission()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabCreate.shrink()
        onClick()
    }

    private fun onClick() {
        binding.fabCreate.setOnClickListener {
            binding.tvNewCV.isVisible = !isAllFabVisible
            binding.tvUploadCV.isVisible = !isAllFabVisible
            if (!isAllFabVisible) {
                binding.fabUploadCV.show()
                binding.fabNewCV.show()
                binding.fabCreate.extend()
                isAllFabVisible = true
            } else {
                binding.fabUploadCV.hide()
                binding.fabNewCV.hide()
                binding.fabCreate.shrink()
                isAllFabVisible = false
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
}