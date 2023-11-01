package com.android.recruitment.features.auth

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recruitment.data.repositories.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val mGoogleSignInClient: GoogleSignInClient,
    private val authRepository: AuthRepository,
) : ViewModel() {

    sealed class Event {
        object NavigateToHome : Event()
    }

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    private val _signInIntent = MutableStateFlow<Intent?>(null)
    val signInIntent: StateFlow<Intent?> = _signInIntent

    fun loginWithGoogle() {
        mGoogleSignInClient.signOut()
        _signInIntent.update { mGoogleSignInClient.signInIntent }
    }

    private fun login(googleToken: String) {
        viewModelScope.launch {
            authRepository.login(googleToken)
                .onSuccess {
                    _event.update { Event.NavigateToHome }
                }
                .onFailure {
                    _message.update { it }
                }
        }
    }

    fun handleLoginResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            account.idToken?.let { login(it) }
            Log.e("xxxx", "handleLoginResult: ${account.idToken}")
        } catch (e: ApiException) {
            _message.update { e.message ?: "" }
        }
    }
}