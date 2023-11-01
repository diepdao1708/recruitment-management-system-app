package com.android.recruitment.features

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    sealed class Event {
        object NavigateToLogin : Event()
        object NavigateToHome : Event()
    }

    private val _event = MutableStateFlow<Event>(Event.NavigateToLogin)
    val event: StateFlow<Event> = _event
}