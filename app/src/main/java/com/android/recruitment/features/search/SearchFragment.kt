package com.android.recruitment.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentSearchBinding
import com.android.recruitment.features.home.HomeViewModel
import com.android.recruitment.features.home.JobAdapter
import com.android.recruitment.features.home.JobUi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val jobAdapter: JobAdapter by lazy {
        JobAdapter(
            listener = object : JobAdapter.OnClickListener {
                override fun onItemClick(jobUi: JobUi) {
                    searchViewModel.navigateToJobDetail(jobUi)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
        observer()

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
                }
            })

        binding.rcvJob.adapter = jobAdapter
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                jobAdapter.updateData(it.recommendJobList)
            }
        }
        searchViewModel.event.observe(viewLifecycleOwner) {
            when (it) {

                is SearchViewModel.Event.NavigateToJobDetail -> {
                    findNavController().navigate(
                        R.id.action_searchFragment_to_jobDetailFragment,
                        it.bundle
                    )
                }

                else -> {}
            }
        }
    }

    private fun onClick() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
        }
    }
}