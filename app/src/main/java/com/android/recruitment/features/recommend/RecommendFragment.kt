package com.android.recruitment.features.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentRecommendBinding
import com.android.recruitment.features.home.JobAdapter
import com.android.recruitment.features.home.JobUi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecommendFragment : Fragment() {

    private lateinit var binding: FragmentRecommendBinding
    private val recommendViewModel: RecommendViewModel by viewModels()
    private val jobAdapter: JobAdapter by lazy {
        JobAdapter(listener = object : JobAdapter.OnClickListener {
            override fun onItemClick(jobUi: JobUi) {
                recommendViewModel.navigateToJobDetail(jobUi)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        onClick()

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_recommendFragment_to_homeFragment)
                }
            })

        binding.rcvRecommendJob.adapter = jobAdapter
    }

    private fun onClick() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_recommendFragment_to_homeFragment)
        }
    }

    private fun observer() {
        recommendViewModel.getAllJob()
        lifecycleScope.launch {
            recommendViewModel.uiState.collectLatest {
                jobAdapter.updateData(it)
            }
        }
        recommendViewModel.event.observe(viewLifecycleOwner) {
            when (it) {

                is RecommendViewModel.Event.NavigateToJobDetail -> {
                    findNavController().navigate(
                        R.id.action_recommendFragment_to_jobDetailFragment,
                        it.bundle
                    )
                }

                else -> {}
            }
        }
    }
}