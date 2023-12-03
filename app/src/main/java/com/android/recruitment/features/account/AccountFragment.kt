package com.android.recruitment.features.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.recruitment.MainActivity
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentAccountBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private val viewModel: AccountViewModel by viewModels()
    private val applicationAdapter: ApplicationAdapter by lazy {
        ApplicationAdapter(
            listener = object : ApplicationAdapter.OnClickListener {
                override fun onItemClick(applicationUi: ApplicationUi) {

                }
            }
        )
    }
    private var experience = "NONE"
    private var major = "NONE"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcvApplication.adapter = applicationAdapter
        binding.tvEditExperience.setOnClickListener {
            val bottomSheet =
                BottomSheet.newInstance(experience, viewModel.listExperience, Type.EXPERIENCE)
            bottomSheet.show(childFragmentManager, BottomSheet::class.java.simpleName)
            bottomSheet.onClick = { item, type ->
                if (type == Type.EXPERIENCE) {
                    viewModel.updateExperience(item)
                }
            }
        }
        binding.tvEditMajor.setOnClickListener {
            val bottomSheet =
                BottomSheet.newInstance(
                    major,
                    viewModel.uiState.value.listCategory.map { it.name },
                    Type.MAJOR
                )
            bottomSheet.show(childFragmentManager, BottomSheet::class.java.simpleName)
            bottomSheet.onClick = { item, type ->
                if (type == Type.MAJOR) {
                    viewModel.updateMajor(item)
                }
            }
        }
        binding.imgAvatar.setOnClickListener {
            val bottomSheet = LogoutBottomSheet.newInstance()
            bottomSheet.show(childFragmentManager, LogoutBottomSheet::class.java.simpleName)
            bottomSheet.onClick = {
                viewModel.logout()
                (activity as MainActivity).logout()
            }
        }
        observer()
    }

    private fun observer() {
        viewModel.getAllApplication()

        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                experience = it.experience
                binding.apply {
                    binding.tvName.text =
                        context?.resources?.getString(R.string.name)
                            ?.let { it1 -> String.format(it1, it.userName) }
                    tvExperience.text = it.experience
                    tvMajor.text = it.major.ifBlank { "NONE" }
                    tvNumberJob.text = it.listApplication.size.toString()
                }
                Glide.with(binding.root)
                    .load(it.avatar)
                    .placeholder(R.drawable.ic_person)
                    .into(binding.imgAvatar)
                applicationAdapter.updateData(it.listApplication)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }
}