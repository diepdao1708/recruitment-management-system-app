package com.android.recruitment.features.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recruitment.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<List<TestUiState>> = MutableStateFlow(listTest)
    val uiState: StateFlow<List<TestUiState>> = _uiState

    private val _testId = MutableLiveData<Int?>(null)
    val testId: LiveData<Int?> = _testId

    private var list = listTest

    sealed class Event {
        data class NavigateToResult(val result: String) : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event?> = _event

    fun update(testUiState: TestUiState) {
        list = list.map { test ->
            if (test.id == testUiState.id) test.copy(point = testUiState.point) else test
        }
        _uiState.update { list.toMutableList() }
    }

    fun predict() {
        val check = list.find { it.point == 0 }
        if (check == null) {
            viewModelScope.launch {
                userRepository.predictFromQuestion(list.map { it.point })
                    .onSuccess {
                        _event.postValue(Event.NavigateToResult(it.message ?: ""))
                    }
                    .onFailure {
                        /* no-op */
                    }
            }
        } else {
            _testId.postValue(check.id)
        }
    }
}