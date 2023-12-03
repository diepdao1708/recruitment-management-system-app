package com.android.recruitment.data.repositories

import com.android.recruitment.data.models.ApplicationResponse
import com.android.recruitment.data.models.Candidate
import com.android.recruitment.data.models.Catagory
import com.android.recruitment.data.models.CommonResponse
import com.android.recruitment.data.models.Resume
import com.android.recruitment.data.models.User
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

    suspend fun cancel(jobId: Int): Result<CommonResponse>

    suspend fun getAllApplication(): Result<List<ApplicationResponse>>
    suspend fun predictFromQuestion(data: List<Int>): Result<CommonResponse>
    suspend fun getCategories(): Result<List<Catagory>>

    suspend fun updateCandidate(candidate: Candidate): Result<User>
    suspend fun getInfo(): Result<User>
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

    override suspend fun cancel(jobId: Int): Result<CommonResponse> {
        return try {
            val response = service.cancel(jobId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllApplication(): Result<List<ApplicationResponse>> {
        return try {
            val response = service.getAllApplication()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun predictFromQuestion(data: List<Int>): Result<CommonResponse> {
        return try {
            val response = service.predictFromQuestion(data)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCategories(): Result<List<Catagory>> {
        return try {
            val response = service.getCategories()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateCandidate(candidate: Candidate): Result<User> {
        return try {
            val response = service.updateCandidate(candidate)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getInfo(): Result<User> {
        return try {
            val response = service.getInfo()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}