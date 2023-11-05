package com.android.recruitment.features.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                binding.apply {
                    tvName.text = it.userName
                    tvExperience.text = it.experience
                    tvMajor.text = it.major
                    tvNumberJob.text = it.numberApplication.toString()
                }
                Glide.with(binding.root)
                    .load(it.avatar)
                    .placeholder(R.drawable.ic_person)
                    .into(binding.imgAvatar)
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