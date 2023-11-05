package com.android.recruitment.data.services

import com.android.recruitment.data.models.Job
import retrofit2.http.GET

interface JobService {

    @GET("/job/all")
    suspend fun getAllJob(): List<Job>
}