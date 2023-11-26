package com.android.recruitment.features.location

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
) : ViewModel() {

    sealed class Event {
        data class NavigateToDetail(val bundle: Bundle? = null) : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event?> = _event

    fun navigateToDetail() {
        _event.postValue(Event.NavigateToDetail())
    }
}