package com.android.recruitment.features.recommend

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.recruitment.features.home.JobUi
import com.android.recruitment.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor() : ViewModel() {

    sealed class Event {
        data class NavigateToJobDetail(val bundle: Bundle) : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event?> = _event

    fun navigateToJobDetail(jobUi: JobUi) {
        val bundle = bundleOf(Constant.SEARCH_TO_JOB_DETAIL_KEY to jobUi)
        _event.postValue(Event.NavigateToJobDetail(bundle))
    }
}