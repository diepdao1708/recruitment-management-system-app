package com.android.recruitment.data.services

import com.android.recruitment.data.models.LoginResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {

    @GET("/auth")
    suspend fun login(@Query("googleToken") googleToken: String): LoginResponse
}