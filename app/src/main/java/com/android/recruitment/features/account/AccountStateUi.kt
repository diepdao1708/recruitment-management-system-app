package com.android.recruitment.features.account

data class AccountStateUi(
    val userName: String = "",
    val avatar: String = "",
    val experience: String = "",
    val major: String = "",
    val listApplication: List<ApplicationUi> = emptyList(),
)

data class ApplicationUi(
    val name: String = "",
    val salary: String = "",
    val terminationDate: String = "",
    val applicationDate: String = "",
    val resumePath: String = "",
    val status: String = "",
)