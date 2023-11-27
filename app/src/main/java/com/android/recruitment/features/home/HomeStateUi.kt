package com.android.recruitment.features.home

import java.io.Serializable

data class HomeStateUi(
    val jobList: List<JobUi> = emptyList(),
    val userName: String = "",
    val avatar: String = "",
)

data class JobUi(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val yearOfExperience: String = "",
    val terminationDate: String = "",
    val salary: String = "",
    val workingTime: String = "",
    val quantity: String = "",
    val gender: String = "",
    val position: String = "",
    val image: Int = -1,
    val statusApplication: String = "",
    val criteriaUiList: List<CriteriaUi> = emptyList(),
    val category: Category = Category(),
) : Serializable

data class CriteriaUi(
    val title: String = "",
    val qualification: String = "",
    val description: String = "",
)

data class Category(
    val description: String = "",
    val name: String = "",
)