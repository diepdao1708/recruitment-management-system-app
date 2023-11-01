package com.android.recruitment.data.services

import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ResumeService {

    @Multipart
    @POST("/upload_resume/")
    suspend fun uploadResume(@Part file: MultipartBody.Part): String

    @GET("/resume/{name_file}")
    suspend fun getResume(@Path("name_file") name_file: String): String
}