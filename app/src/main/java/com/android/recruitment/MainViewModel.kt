package com.android.recruitment

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.android.recruitment.features.SplashViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    fun logout() {
        sharedPreferences.edit {
            putString(SplashViewModel.ACCESS_TOKEN, "").apply()
        }
    }
}