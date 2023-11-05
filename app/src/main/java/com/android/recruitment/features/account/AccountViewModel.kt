package com.android.recruitment.features.account

import androidx.lifecycle.ViewModel
import com.android.recruitment.data.models.SavedAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val savedAccount: SavedAccount,
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
}