package com.android.recruitment.features.user_follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentUserFollowBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserFollowFragment : Fragment() {
    private lateinit var binding: FragmentUserFollowBinding
    private val viewModel: UserFollowViewModel by viewModels()
    private val userFollowAdapter: UserFollowAdapter by lazy {
        UserFollowAdapter(
            listener = object : UserFollowAdapter.OnClickListener {
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
        binding = FragmentUserFollowBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcvUser.adapter = userFollowAdapter
        observer()
    }

    private fun observer() {
        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is UserFollowViewModel.Event.NavigateToDetail -> {
                    findNavController().navigate(R.id.action_userFollowFragment_to_userDetailFragment)
                }

                else -> {
                    /* no-op */
                }
            }
        }
    }
}