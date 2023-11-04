package com.android.recruitment.features.job_detail

import androidx.lifecycle.ViewModel
import com.android.recruitment.features.home.JobUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class JobDetailViewModel @Inject constructor() : ViewModel() {

    private val _jobUi = MutableStateFlow(JobUi())
    val jobUi: StateFlow<JobUi> = _jobUi

    fun setData(jobUi: JobUi) {
        _jobUi.update { jobUi }
    }
}