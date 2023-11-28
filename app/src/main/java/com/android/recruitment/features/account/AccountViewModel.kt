package com.android.recruitment.features.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recruitment.data.models.SavedAccount
import com.android.recruitment.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val savedAccount: SavedAccount,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountStateUi())
    val uiState: StateFlow<AccountStateUi> = _uiState

    init {
        _uiState.update {
            it.copy(
                userName = savedAccount.user?.name ?: "",
                avatar = savedAccount.user?.avatar ?: "",
                experience = (savedAccount.user?.yearOfExperience ?: 0).toString(),
                major = savedAccount.user?.major ?: "",
            )
        }
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
}