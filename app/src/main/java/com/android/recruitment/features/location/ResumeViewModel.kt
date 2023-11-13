package com.android.recruitment.features.location

import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recruitment.data.repositories.UserRepository
import com.android.recruitment.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    sealed class Event {
        data class NavigateToDetail(val bundle: Bundle) : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event?> = _event


    private val _uiState = MutableStateFlow(ResumeUiState())
    val uiState: MutableStateFlow<ResumeUiState> = _uiState

    init {
        getAllResume()
    }

    private fun getAllResume() {
        viewModelScope.launch {
            userRepository.getAllResume()
                .onSuccess { resumeList ->
                    _uiState.update {
                        it.copy(resumeList = resumeList.map { resume ->
                            val name = resume.path?.split("\\")?.getOrNull(1)
                            ItemResume(
                                name = name ?: "",
                                path = resume.path ?: ""
                            )
                        })
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(message = it.message) }
                }
        }
    }

    fun navigateToDetail(uri: Uri) {
        val bundle = bundleOf(Constant.RESUME_TO_DETAIL_KEY to uri)
        _event.postValue(Event.NavigateToDetail(bundle))
    }

    fun navigationToDetail(resume: ItemResume) {
        val bundle = bundleOf(Constant.RESUME_TO_DETAIL_KEY to resume)
        _event.postValue(Event.NavigateToDetail(bundle))
    }
}