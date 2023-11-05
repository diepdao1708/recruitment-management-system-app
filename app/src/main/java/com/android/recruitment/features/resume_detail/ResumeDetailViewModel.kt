package com.android.recruitment.features.resume_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recruitment.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ResumeDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    sealed class Event {
        object BackToResume : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun uploadResume(file: File) {
        viewModelScope.launch {
            userRepository.uploadResume(file).onSuccess {
                    _event.postValue(Event.BackToResume)
                }.onFailure {
                    _message.postValue(it.message)
                }
        }
    }
}