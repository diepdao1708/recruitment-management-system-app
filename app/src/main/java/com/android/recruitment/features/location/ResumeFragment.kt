package com.android.recruitment.features.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.recruitment.databinding.FragmentResumeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResumeFragment : Fragment() {
    private lateinit var binding: FragmentResumeBinding
    private val viewModel: ResumeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResumeBinding.inflate(inflater, container, false)

        return binding.root
    }
}