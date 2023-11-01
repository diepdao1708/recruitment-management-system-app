package com.android.recruitment.data.repositories

import com.android.recruitment.data.services.ResumeService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.File
import javax.inject.Inject


interface ResumeRepository {
    suspend fun uploadResume(file: File): Result<String>
}

class ResumeRepositoryImpl @Inject constructor(
    retrofit: Retrofit,
) : ResumeRepository {

    private val service = retrofit.create(ResumeService::class.java)

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
}