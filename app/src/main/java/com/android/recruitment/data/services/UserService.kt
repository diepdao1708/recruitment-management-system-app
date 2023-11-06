package com.android.recruitment.data.services

import com.android.recruitment.data.models.CommonResponse
import com.android.recruitment.data.models.Resume
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UserService {

    @Multipart
    @POST("/candidate/upload_resume/")
    suspend fun uploadResume(@Part file: MultipartBody.Part): String

    @GET("/candidate/all_my_resume")
    suspend fun getAllResume(): List<Resume>

    @POST("/application/")
    suspend fun apply(@Query("job_id") jobId: String, @Query("resumePath") resumePath: String): CommonResponse
}