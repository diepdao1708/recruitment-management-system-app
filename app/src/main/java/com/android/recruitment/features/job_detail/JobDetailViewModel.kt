package com.android.recruitment.features.job_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recruitment.data.models.SavedAccount
import com.android.recruitment.data.repositories.UserRepository
import com.android.recruitment.features.home.JobUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val savedAccount: SavedAccount,
) : ViewModel() {

    private val _jobUi = MutableStateFlow(JobUi())
    val jobUi: StateFlow<JobUi> = _jobUi

    private val _message = MutableLiveData<String?>(null)
    val message: LiveData<String?> = _message

    fun setData(jobUi: JobUi) {
        _jobUi.update { jobUi }
    }

    fun apply(jobId: String) {
        viewModelScope.launch {
            userRepository.apply(jobId, savedAccount.user?.resumePath ?: "")
                .onSuccess {
                    _message.postValue(it.message ?: "")
                }
                .onFailure {
                    _message.postValue(it.message)
                }
        }
    }

    fun cancel(jobId: Int) {
        viewModelScope.launch {
            userRepository.cancel(jobId)
                .onSuccess {
                    _message.postValue(it.message ?: "")
                }
                .onFailure {
                    _message.postValue(it.message)
                }
        }
    }
}