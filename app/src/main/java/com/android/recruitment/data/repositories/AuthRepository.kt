package com.android.recruitment.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.android.recruitment.data.models.SavedAccount
import com.android.recruitment.data.services.AuthService
import com.android.recruitment.features.SplashViewModel
import com.google.gson.Gson
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(googleToken: String): Result<Unit>
}

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val sharedPreferences: SharedPreferences,
    private val savedAccount: SavedAccount,
) : AuthRepository {

    companion object {
        const val ACCOUNT_INFO = "ACCOUNT_INFO"
    }

    override suspend fun login(googleToken: String): Result<Unit> {
        return try {
            val loginResponse = authService.login(googleToken)
            sharedPreferences.edit {
                putString(SplashViewModel.ACCESS_TOKEN, loginResponse.accessToken).apply()
            }
            Gson().apply {
                val userJson = toJson(loginResponse.user)
                val editor: SharedPreferences.Editor? = sharedPreferences.edit()
                editor?.putString(ACCOUNT_INFO, userJson)
                editor?.apply()
            }
            savedAccount.accessToken = loginResponse.accessToken
            savedAccount.user = loginResponse.user
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}