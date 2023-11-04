package com.android.recruitment.features.job_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentJobDetailBinding
import com.android.recruitment.features.home.JobUi
import com.android.recruitment.utils.Constant
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

        jobFromHome = arguments?.getSerializable(Constant.HOME_TO_JOB_DETAIL_KEY) as? JobUi
        jobFromRecommend = arguments?.getSerializable(Constant.RECOMMEND_TO_JOB_DETAIL_KEY) as? JobUi
        jobFromSearch = arguments?.getSerializable(Constant.SEARCH_TO_JOB_DETAIL_KEY) as? JobUi

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
        binding.btnBack.setOnClickListener {
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
                    tvPosition.text = it.position
                }
                criteriaAdapter.updateData(it.criteriaUiList)
            }
        }
    }
}