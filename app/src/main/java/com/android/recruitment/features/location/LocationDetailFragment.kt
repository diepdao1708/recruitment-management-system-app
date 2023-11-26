package com.android.recruitment.features.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentDetailLocationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailLocationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_locationDetailFragment_to_locationFragment)
        }
    }
}