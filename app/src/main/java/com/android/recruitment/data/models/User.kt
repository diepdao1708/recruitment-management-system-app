package com.android.recruitment.data.models

data class User(
    val id: Int,
    val phone: String?,
    val description: String?,
    val yearOfExperience: Int?,
    val dateOfBirth: String?,
    val resumePath: String?,
    val name: String?,
    val address: String?,
    val email: String?,
    val avatar: String?,
    val major: String?,
)
