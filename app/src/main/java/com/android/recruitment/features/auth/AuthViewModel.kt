package com.android.recruitment.features.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    sealed class Event {
        object NavigateToHome : Event()
        object NavigateToRegister : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event?> = _event

    fun login() {
        _event.postValue(Event.NavigateToHome)
    }

    fun register() {
        _event.postValue(Event.NavigateToRegister)
    }
}