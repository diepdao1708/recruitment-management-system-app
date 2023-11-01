package com.android.recruitment.data.repositories

import com.android.recruitment.data.models.SavedAccount
import com.android.recruitment.data.services.AuthService
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(googleToken: String): Result<Unit>
}

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val savedAccount: SavedAccount,
) : AuthRepository {


    override suspend fun login(googleToken: String): Result<Unit> {
        return try {
            val loginResponse = authService.login(googleToken)
            savedAccount.accessToken = loginResponse.access
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}