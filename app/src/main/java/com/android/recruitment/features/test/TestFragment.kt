package com.android.recruitment.features.test

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
import com.android.recruitment.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding
    private val testViewModel: TestViewModel by viewModels()
    private val testAdapter: TestAdapter by lazy {
        TestAdapter(listener = object : TestAdapter.OnClickListener {
            override fun onClick(test: TestUiState) {
                testViewModel.update(test)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
        observer()
        binding.rcvQuestion.adapter = testAdapter
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_testFragment_to_homeFragment)
                }
            })

    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            testViewModel.uiState.collectLatest {
                testAdapter.submitList(it)
            }
        }
        testViewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is TestViewModel.Event.NavigateToResult -> {
                    findNavController().navigate(
                        TestFragmentDirections.actionTestFragmentToResultFragment(
                            it.result
                        )
                    )
                }


                else -> {}
            }
        }
        testViewModel.testId.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rcvQuestion.scrollToPosition(it - 1)
            }
        }
    }

    private fun onClick() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_testFragment_to_homeFragment)
        }
        binding.tvDone.setOnClickListener {
            testViewModel.predict()
        }
    }
}