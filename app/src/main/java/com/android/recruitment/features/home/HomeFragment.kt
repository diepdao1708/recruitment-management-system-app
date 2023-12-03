package com.android.recruitment.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val jobAdapter: JobAdapter by lazy {
        JobAdapter(listener = object : JobAdapter.OnClickListener {
            override fun onItemClick(jobUi: JobUi) {
                viewModel.navigateToJobDetail(jobUi)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        onClick()

        binding.rcvRecommendJob.adapter = jobAdapter
    }

    private fun onClick() {
        binding.tvAllJob.setOnClickListener {
            viewModel.navigateToRecommend()
        }
        binding.clSearch.setOnClickListener {
            viewModel.navigateToSearch()
        }
        binding.clAccount.setOnClickListener {
            viewModel.navigateToAccount()
        }
        binding.clTest.setOnClickListener {
            viewModel.navigateToTest()
        }
    }

    private fun observer() {
        viewModel.getRecommendJob()
        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                binding.tvName.text =
                    context?.resources?.getString(R.string.name)
                        ?.let { it1 -> String.format(it1, it.userName) }
                Glide.with(binding.root)
                    .load(it.avatar)
                    .placeholder(R.drawable.ic_person)
                    .into(binding.imgAvatar)
                jobAdapter.updateData(it.jobList)
            }
        }

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                HomeViewModel.Event.NavigateToRecommend -> {
                    findNavController().navigate(R.id.action_homeFragment_to_recommendFragment)
                }

                HomeViewModel.Event.NavigateToSearch -> {
                    findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                }

                HomeViewModel.Event.NavigateToTest -> {
                    findNavController().navigate(R.id.action_homeFragment_to_testFragment)
                }

                HomeViewModel.Event.NavigateToAccount -> {
                    findNavController()
                        .navigate(
                            R.id.accountFragment,
                            null,
                            NavOptions.Builder()
                                .setPopUpTo(R.id.homeFragment, true)
                                .build()
                        )
                }

                is HomeViewModel.Event.NavigateToJobDetail -> {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_jobDetailFragment,
                        it.bundle
                    )
                }

                else -> {}
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}