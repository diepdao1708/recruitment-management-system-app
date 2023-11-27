package com.android.recruitment.data.services

import com.android.recruitment.data.models.Job
import retrofit2.http.GET

interface JobService {

    @GET("/job/all_valid")
    suspend fun getAllJob(): List<Job>

    @GET("/job/all_recommend")
    suspend fun getRecommendJob(): List<Job>
}