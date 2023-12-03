package com.android.recruitment.features.account

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recruitment.data.models.Candidate
import com.android.recruitment.data.repositories.UserRepository
import com.android.recruitment.features.SplashViewModel
import com.android.recruitment.features.home.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountStateUi())
    val uiState: StateFlow<AccountStateUi> = _uiState

    val listExperience = listOf(
        "0 Year",
        "1 Year",
        "2 Year",
        "3 Year",
        "4 Year",
        "5 Year",
        "6 Year",
        "7 Year",
        "8 Year",
        "9 Year",
        "10 Year"
    )

    init {
        getInfo()
        getCategories()
    }

    fun getAllApplication() {
        viewModelScope.launch {
            userRepository.getAllApplication()
                .onSuccess { applications ->
                    _uiState.update {
                        it.copy(listApplication = applications.map { application ->
                            ApplicationUi(
                                name = application.job?.name ?: "",
                                salary = application.job?.salary ?: "",
                                terminationDate = ("Termination date: " + application.job?.terminationDate),
                                applicationDate = application.application?.applicationDate ?: "",
                                resumePath = application.application?.resumePath ?: "",
                                status = application.application?.status?.code ?: "",
                            )
                        })
                    }
                }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            userRepository.getCategories()
                .onSuccess { categories ->
                    _uiState.update {
                        it.copy(listCategory = categories.map { catagory ->
                            Category(
                                catagory.id ?: 0,
                                name = catagory.name ?: "",
                                description = catagory.description ?: "",
                            )
                        })
                    }
                }
                .onFailure { }
        }
    }

    fun updateExperience(item: String) {
        viewModelScope.launch {
            userRepository.updateCandidate(
                Candidate(
                    yearOfExperience = item.substring(0, 1),
                    major = _uiState.value.major,
                )
            )
                .onSuccess {
                    _uiState.update { it.copy(experience = item) }
                }
                .onFailure { }
        }
    }

    fun updateMajor(item: String) {
        viewModelScope.launch {
            userRepository.updateCandidate(
                Candidate(
                    yearOfExperience = _uiState.value.experience.substring(0, 1),
                    major = item,
                )
            )
                .onSuccess {
                    _uiState.update { it.copy(major = item) }
                }
                .onFailure { }
        }
    }

    private fun getInfo() {
        viewModelScope.launch {
            userRepository.getInfo()
                .onSuccess { user ->
                    _uiState.update {
                        it.copy(
                            userName = user.name ?: "",
                            avatar = user.avatar ?: "",
                            experience = "${user.yearOfExperience} Year",
                            major = user.major ?: "",
                        )
                    }
                }
                .onFailure { }
        }
    }

    fun logout() {
        sharedPreferences.edit {
            putString(SplashViewModel.ACCESS_TOKEN, "").apply()
        }
    }
}