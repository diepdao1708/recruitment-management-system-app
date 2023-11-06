package com.android.recruitment.data.models

data class Job(
    val id: Int?,
    val numberOfCandidate: Int?,
    val hireDate: String?,
    val description: String?,
    val name: String?,
    val yearOfExperience: String?,
    val terminationDate: String?,
    val criterias: List<Criteria>?,
    val categories: List<Catagory>?,
    val salary: String?,
    val workingTime: String?,
    val gender: String?,
    val position: String?,
    val status_application: StatusApplication?,
)