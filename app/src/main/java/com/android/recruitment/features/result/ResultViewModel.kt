package com.android.recruitment.features.result

import androidx.lifecycle.ViewModel
import com.android.recruitment.data.repositories.UserRepository
import com.android.recruitment.features.home.JobUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<List<JobUi>> = MutableStateFlow(emptyList())
    val uiState: StateFlow<List<JobUi>> = _uiState


    fun predict() {

    }
}