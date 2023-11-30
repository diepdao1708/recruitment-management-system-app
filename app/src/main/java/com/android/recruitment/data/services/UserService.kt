package com.android.recruitment.data.services

import com.android.recruitment.data.models.ApplicationResponse
import com.android.recruitment.data.models.CommonResponse
import com.android.recruitment.data.models.Resume
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @Multipart
    @POST("/candidate/upload_resume/")
    suspend fun uploadResume(@Part file: MultipartBody.Part): String

    @GET("/candidate/all_my_resume")
    suspend fun getAllResume(): List<Resume>

    @POST("/application/")
    suspend fun apply(
        @Query("job_id") jobId: String,
        @Query("resumePath") resumePath: String
    ): CommonResponse

    @PUT("/application/update_status_by_job_id/{id}/?status_application=CANCELED")
    suspend fun cancel(@Path("id") id: Int): CommonResponse

    @GET("/application/all_my_application")
    suspend fun getAllApplication(): List<ApplicationResponse>

    @POST("/predict_from_question")
    suspend fun predictFromQuestion(@Body data: List<Int>): CommonResponse
}