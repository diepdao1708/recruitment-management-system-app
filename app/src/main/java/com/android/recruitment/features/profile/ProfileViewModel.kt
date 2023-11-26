package com.android.recruitment.features.profile

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    sealed class Event {
        data class NavigateToEdit(val bundle: Bundle? = null) : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event?> = _event

    fun navigateToEdit() {
        _event.postValue(Event.NavigateToEdit())
    }
}