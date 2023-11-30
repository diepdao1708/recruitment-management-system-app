package com.android.recruitment.features.result

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding
    private val resultViewModel: ResultViewModel by viewModels()
    val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("xxxx", "onViewCreated: ${args.result}")
        onClick()
        observer()

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
                }
            })

    }

    private fun observer() {

    }

    private fun onClick() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
        }
    }
}