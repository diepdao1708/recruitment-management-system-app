package com.android.recruitment.data.repositories

import com.android.recruitment.data.models.Job
import com.android.recruitment.data.services.JobService
import javax.inject.Inject

interface JobRepository {
    suspend fun getAllJob(): Result<List<Job>>
}

class JobRepositoryImpl @Inject constructor(
    private val service: JobService,
) : JobRepository {
    override suspend fun getAllJob(): Result<List<Job>> {
        return try {
            val response = service.getAllJob()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}