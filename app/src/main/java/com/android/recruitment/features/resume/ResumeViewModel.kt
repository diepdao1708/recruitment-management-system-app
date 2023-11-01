package com.android.recruitment.features.resume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recruitment.data.repositories.ResumeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val resumeRepository: ResumeRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResumeUiState())
    val uiState: MutableStateFlow<ResumeUiState> = _uiState

    fun uploadResume(file: File) {
        viewModelScope.launch {
            resumeRepository.uploadResume(file)
                .onSuccess { response ->
                    _uiState.update { ResumeUiState(response) }
                }
                .onFailure {
                    _uiState.update { ResumeUiState(it.message) }
                }
        }
    }
}