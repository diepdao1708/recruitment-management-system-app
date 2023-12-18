package com.android.recruitment.features.job_detail

import android.os.Build
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
import com.android.recruitment.databinding.FragmentJobDetailBinding
import com.android.recruitment.features.home.JobUi
import com.android.recruitment.utils.Constant
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JobDetailFragment : Fragment() {

    private lateinit var binding: FragmentJobDetailBinding
    private val viewModel: JobDetailViewModel by viewModels()
    private val criteriaAdapter: CriteriaAdapter by lazy { CriteriaAdapter() }
    private var jobFromHome: JobUi? = null
    private var jobFromRecommend: JobUi? = null
    private var jobFromSearch: JobUi? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJobDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobFromHome = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(Constant.HOME_TO_JOB_DETAIL_KEY, JobUi::class.java)
        } else {
            arguments?.getSerializable(Constant.HOME_TO_JOB_DETAIL_KEY) as? JobUi
        }
        jobFromRecommend = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(Constant.RECOMMEND_TO_JOB_DETAIL_KEY, JobUi::class.java)
        } else {
            arguments?.getSerializable(Constant.RECOMMEND_TO_JOB_DETAIL_KEY) as? JobUi
        }
        jobFromSearch = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(Constant.SEARCH_TO_JOB_DETAIL_KEY, JobUi::class.java)
        } else {
            arguments?.getSerializable(Constant.SEARCH_TO_JOB_DETAIL_KEY) as? JobUi
        }

        if (jobFromHome != null) {
            viewModel.setData(jobFromHome!!)
        }
        if (jobFromRecommend != null) {
            viewModel.setData(jobFromRecommend!!)
        }
        if (jobFromSearch != null) {
            viewModel.setData(jobFromSearch!!)
        }

        binding.rcvCriteria.adapter = criteriaAdapter
        onClick()
        observer()
    }

    private fun onClick() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    back()
                }
            })
        binding.btnBack.setOnClickListener {
            back()
        }
        binding.tvApply.setOnClickListener {
            val jobId = if (jobFromHome != null) jobFromHome!!.id
            else if (jobFromSearch != null) jobFromSearch!!.id
            else jobFromRecommend!!.id
            if (binding.tvApply.text.toString().lowercase() == "pending") {
                viewModel.cancel(jobId)
            } else if (binding.tvApply.text.toString().lowercase() == "apply") {
                viewModel.apply(jobId = jobId.toString())
            }
        }
    }

    private fun back() {
        when {
            jobFromHome != null -> {
                findNavController().navigate(R.id.action_jobDetailFragment_to_homeFragment)
            }

            jobFromRecommend != null -> {
                findNavController().navigate(R.id.action_jobDetailFragment_to_recommendFragment)
            }

            jobFromSearch != null -> {
                findNavController().navigate(R.id.action_jobDetailFragment_to_searchFragment)
            }
        }
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.jobUi.collectLatest {
                binding.apply {
                    tvNameJob.text = it.name
                    tvSalary.text = it.salary
                    tvExperience.text = it.yearOfExperience
                    tvTime.text = it.workingTime
                    tvQuantity.text = it.quantity
                    tvGender.text = it.gender
                    if (it.statusApplication.isNotBlank()) {
                        tvApply.isEnabled = it.statusApplication == "PENDING"
                        tvApply.text = it.statusApplication
                    }
                    Glide.with(binding.root)
                        .load(it.imagePath)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(binding.imgJob)
                }
                criteriaAdapter.updateData(it.criteriaUiList)
            }
        }
        viewModel.message.observe(viewLifecycleOwner) {
            if (it == "apply success") {
                binding.tvApply.text = "PENDING"
            } else if (it == "update success") {
                binding.tvApply.text = "APPLY"
            }
        }
    }
}