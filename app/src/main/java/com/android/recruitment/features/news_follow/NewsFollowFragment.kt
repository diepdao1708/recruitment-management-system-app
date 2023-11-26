package com.android.recruitment.features.news_follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentNewsFollowBinding
import com.android.recruitment.features.home.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFollowFragment : Fragment() {
    private lateinit var binding: FragmentNewsFollowBinding
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(listener = object : NewsAdapter.OnClickListener {
            override fun onItemClick() {
                findNavController().navigate(R.id.action_newsFollowFragment_to_userDetailFragment)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcvNews.adapter = newsAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFollowBinding.inflate(inflater, container, false)
        return binding.root
    }
}