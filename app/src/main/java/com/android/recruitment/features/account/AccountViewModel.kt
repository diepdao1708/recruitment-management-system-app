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

}