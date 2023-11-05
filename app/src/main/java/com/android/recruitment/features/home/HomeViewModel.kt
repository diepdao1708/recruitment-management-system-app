package com.android.recruitment.features.home

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recruitment.data.models.SavedAccount
import com.android.recruitment.data.repositories.JobRepository
import com.android.recruitment.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedAccount: SavedAccount,
    private val jobRepository: JobRepository,
) : ViewModel() {

    sealed class Event {
        object NavigateToRecommend : Event()
        object NavigateToSearch : Event()
        object NavigateToAccount : Event()
        data class NavigateToJobDetail(val bundle: Bundle) : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event?> = _event

    private val _uiState = MutableStateFlow(HomeStateUi())
    val uiState: StateFlow<HomeStateUi> = _uiState

    init {
        _uiState.update {
            it.copy(
                userName = savedAccount.user?.name ?: "",
                avatar = savedAccount.user?.avatar ?: "",
            )
        }
    }

    fun getAllJob() {
        viewModelScope.launch {
            jobRepository.getAllJob()
                .onSuccess { jobList ->
                    _uiState.update {
                        it.copy(recommendJobList = jobList.map { job ->
                            JobUi(
                                name = job.name ?: "",
                                description = job.description ?: "",
                                yearOfExperience = job.yearOfExperience ?: "",
                                terminationDate = job.terminationDate ?: "",
                                salary = job.salary ?: "",
                                workingTime = job.workingTime ?: "",
                                quantity = (job.numberOfCandidate ?: 0).toString(),
                                gender = job.gender ?: "",
                                position = job.position ?: "",
                                criteriaUiList = job.criterias?.map { criteria ->
                                    CriteriaUi(
                                        title = criteria.title ?: "",
                                        qualification = criteria.qualification ?: ""
                                    )
                                } ?: emptyList()
                            )
                        })
                    }
                }
                .onFailure { }
        }
    }

    fun navigateToRecommend() {
        _event.postValue(Event.NavigateToRecommend)
    }

    fun navigateToSearch() {
        _event.postValue(Event.NavigateToSearch)
    }

    fun navigateToJobDetail(jobUi: JobUi) {
        val bundle = bundleOf(Constant.HOME_TO_JOB_DETAIL_KEY to jobUi)
        _event.postValue(Event.NavigateToJobDetail(bundle))
    }

    fun navigateToAccount() {
        _event.postValue(Event.NavigateToAccount)
    }
}