package com.android.recruitment.features.home

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.recruitment.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    sealed class Event {
        object NavigateToRecommend : Event()
        object NavigateToSearch : Event()
        data class NavigateToJobDetail(val bundle: Bundle) : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event?> = _event

    private val _uiState = MutableStateFlow(HomeStateUi())
    val uiState: StateFlow<HomeStateUi> = _uiState

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
}