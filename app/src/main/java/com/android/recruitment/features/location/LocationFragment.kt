package com.android.recruitment.features.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentLocationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationFragment : Fragment() {
    private lateinit var binding: FragmentLocationBinding
    private val viewModel: LocationViewModel by viewModels()
    private val locationAdapter: LocationAdapter by lazy {
        LocationAdapter(
            listener = object : LocationAdapter.OnClickListener {
                override fun onItemClick() {
                    viewModel.navigateToDetail()
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcvLocations.adapter = locationAdapter
        observer()
    }

    private fun observer() {
        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is LocationViewModel.Event.NavigateToDetail -> {
                    findNavController().navigate(R.id.action_locationFragment_to_locationDetailFragment)
                }

                else -> {
                    /* no-op */
                }
            }
        }
    }
}