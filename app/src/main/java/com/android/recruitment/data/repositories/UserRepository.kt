package com.android.recruitment.data.repositories

import com.android.recruitment.data.models.CommonResponse
import com.android.recruitment.data.models.Resume
import com.android.recruitment.data.services.UserService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


interface UserRepository {
    suspend fun uploadResume(file: File): Result<String>
    suspend fun getAllResume(): Result<List<Resume>>
    suspend fun apply(jobId: String, resumePath: String): Result<CommonResponse>
}

class UserRepositoryImpl @Inject constructor(
    private val service: UserService,
) : UserRepository {

    override suspend fun uploadResume(file: File): Result<String> {
        return try {
            val requestBody = RequestBody.create("*/*".toMediaTypeOrNull(), file)
            val response = service.uploadResume(
                MultipartBody.Part.createFormData(
                    "file",
                    file.name,
                    requestBody
                )
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllResume(): Result<List<Resume>> {
        return try {
            val response = service.getAllResume()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun apply(jobId: String, resumePath: String): Result<CommonResponse> {
        return try {
            val response = service.apply(jobId, resumePath)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}