package com.android.recruitment.features.auth

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event?> = _event

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _signInIntent = MutableStateFlow<Intent?>(null)
    val signInIntent: StateFlow<Intent?> = _signInIntent

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun loginWithGoogle() {
        _isLoading.postValue(true)
        mGoogleSignInClient.signOut()
        _signInIntent.update { mGoogleSignInClient.signInIntent }
    }

    private fun login(googleToken: String) {
        viewModelScope.launch {
            authRepository.login(googleToken)
                .onSuccess {
                    _isLoading.postValue(false)
                    _event.postValue(Event.NavigateToHome)
                }
                .onFailure {
                    _isLoading.postValue(false)
                    _message.postValue(it.message)
                }
        }
    }

    fun handleLoginResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            account.idToken?.let { login(it) }
        } catch (e: ApiException) {
            _message.postValue(e.message)
        }
    }
}