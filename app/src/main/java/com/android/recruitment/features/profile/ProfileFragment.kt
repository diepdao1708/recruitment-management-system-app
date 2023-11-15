package com.android.recruitment.features.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentProfileBinding
import com.android.recruitment.features.home.NewsAdapter

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(listener = object : NewsAdapter.OnClickListener {
            override fun onItemClick() {

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
    }

    private fun onClick() {
        binding.imgAvatar.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_accountFragment)
        }
    }
}