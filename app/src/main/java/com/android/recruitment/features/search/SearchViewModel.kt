package com.android.recruitment.features.search

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recruitment.data.repositories.JobRepository
import com.android.recruitment.features.home.Category
import com.android.recruitment.features.home.CriteriaUi
import com.android.recruitment.features.home.JobUi
import com.android.recruitment.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val jobRepository: JobRepository,
) : ViewModel() {

    sealed class Event {
        data class NavigateToJobDetail(val bundle: Bundle) : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event?> = _event

    private val _uiState: MutableStateFlow<List<JobUi>> = MutableStateFlow(emptyList())
    val uiState: StateFlow<List<JobUi>> = _uiState

    fun navigateToJobDetail(jobUi: JobUi) {
        val bundle = bundleOf(Constant.SEARCH_TO_JOB_DETAIL_KEY to jobUi)
        _event.postValue(Event.NavigateToJobDetail(bundle))
    }

    fun getAllJob() {
        viewModelScope.launch {
            jobRepository.getAllJob()
                .onSuccess { jobList ->
                    _uiState.update {
                        jobList.map { job ->
                            val image = job.imagePath?.split("\\")
                            JobUi(
                                id = job.id ?: 0,
                                name = job.name ?: "",
                                description = job.description ?: "",
                                yearOfExperience = ("Year of experience: " + job.yearOfExperience),
                                terminationDate = ("Termination date: " + job.terminationDate),
                                salary = job.salary ?: "",
                                workingTime = job.workingTime ?: "",
                                quantity = (job.numberOfCandidate ?: 0).toString(),
                                gender = job.gender ?: "",
                                statusApplication = job.status_application?.code ?: "",
                                criteriaUiList = job.criterias?.map { criteria ->
                                    CriteriaUi(
                                        title = criteria.title ?: "",
                                        qualification = criteria.qualification ?: ""
                                    )
                                } ?: emptyList(),
                                category = Category(
                                    description = job.description ?: "",
                                    name = job.name ?: "",
                                ),
                                imagePath = "${Constant.BASE_URL}/${image?.get(0)}/${image?.get(1)}",
                            )
                        }
                    }
                }
                .onFailure { }
        }
    }
}