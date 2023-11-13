package com.android.recruitment.features.add_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.recruitment.databinding.FragmentAddNewsBinding

class AddNewsFragment : Fragment() {

    private lateinit var binding: FragmentAddNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewsBinding.inflate(inflater, container, false)
        return binding.root
    }
}