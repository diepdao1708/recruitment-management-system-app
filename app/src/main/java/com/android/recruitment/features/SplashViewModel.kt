package com.android.recruitment.features

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recruitment.data.models.SavedAccount
import com.android.recruitment.data.models.User
import com.android.recruitment.data.repositories.AuthRepositoryImpl.Companion.ACCOUNT_INFO
import com.android.recruitment.data.repositories.UserRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val savedAccount: SavedAccount,
    private val userRepository: UserRepository,
) : ViewModel() {

    companion object {
        const val DEFAULT_VALUE = "null"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }

    init {
        checkLogin()
    }

    sealed class Event {
        object NavigateToLogin : Event()
        object NavigateToOnboard : Event()
        object NavigateToHome : Event()
    }

    private val _events = Channel<Event>(capacity = Channel.UNLIMITED)
    val events = _events.receiveAsFlow()

    private fun checkLogin() {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, DEFAULT_VALUE)
        val presetJson = sharedPreferences.getString(ACCOUNT_INFO, null)
        val type = object : TypeToken<User?>() {}.type
        val user: User = Gson().fromJson(presetJson, type)
        savedAccount.accessToken = accessToken
        savedAccount.user = user
        Log.d("xxxx", accessToken.toString())
        viewModelScope.launch {
            userRepository.getAllResume()
                .onSuccess {
                    _events.trySend(Event.NavigateToHome)
                }.onFailure {
                    if (accessToken == null || accessToken == DEFAULT_VALUE) {
                        _events.trySend(Event.NavigateToOnboard)
                    } else {
                        _events.trySend(Event.NavigateToLogin)
                    }
                }
        }
    }
}