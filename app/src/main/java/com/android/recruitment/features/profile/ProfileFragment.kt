package com.android.recruitment.features.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val newsAdapter: MyNewsAdapter by lazy {
        MyNewsAdapter(listener = object : MyNewsAdapter.OnClickListener {
            override fun onDelete() {

            }

            override fun onFavorite() {

            }

            override fun onEdit() {
                viewModel.navigateToEdit()
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcvNews.adapter = newsAdapter
        onClick()
        observer()
    }

    private fun observer() {
        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is ProfileViewModel.Event.NavigateToEdit -> {
                    findNavController().navigate(R.id.action_profileFragment_to_editNewsFragment)
                }

                else -> {}
            }
        }
    }

    private fun onClick() {
        binding.imgAvatar.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_accountFragment)
        }
        binding.tvTinDaQuanTam.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_newsFollowFragment)
        }
        binding.tvNguoiDungQuanTam.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userFollowFragment)
        }
    }
}