package com.android.recruitment.data.services

import com.android.recruitment.data.models.LoginResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {

    @POST("/auth")
    suspend fun login(@Path("googleToken") googleToken: String): LoginResponse
}