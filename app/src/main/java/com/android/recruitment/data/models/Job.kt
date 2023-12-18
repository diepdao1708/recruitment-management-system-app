package com.android.recruitment.data.models

import com.google.gson.annotations.SerializedName

data class Job(
    val id: Int?,
    val numberOfCandidate: Int?,
    val description: String?,
    val name: String?,
    val yearOfExperience: String?,
    val terminationDate: String?,
    val criterias: List<Criteria>?,
    val category: Catagory?,
    val salary: String?,
    val workingTime: String?,
    val gender: String?,
    @SerializedName("imagePath")
    val imagePath: String?,
    val status_application: StatusApplication?,
)