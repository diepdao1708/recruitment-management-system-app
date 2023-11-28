package com.android.recruitment.data.models

data class Application(
    val job_id: Int?,
    val candidate_id: Int?,
    val applicationDate: String?,
    val description: String?,
    val resumePath: String?,
    val id: Int?,
    val result: String?,
    val status: StatusApplication?,
)

data class ApplicationResponse(
    val application: Application?,
    val job: Job?,
)