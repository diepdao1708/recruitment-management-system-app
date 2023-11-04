package com.android.recruitment.features.home

import java.io.Serializable

data class HomeStateUi(
    val recommendJobList: List<JobUi> = listOf(
        JobUi(
            name = "Android (Kotlin, Java)",
            salary = "15 - 20 trieu",
            yearOfExperience = "Nam kinh nghiem: 1 nam",
            workingTime = "Toan thoi gian",
            quantity = "2 nguoi",
            gender = "Khong yeu cau",
            position = "Nhan vien",
            terminationDate = "Ngay het han: 04/11/2023",
            criteriaUiList = listOf(
                CriteriaUi(
                    title = "Mo ta cong vien",
                    qualification = "- Tham gia nghien cuu, xay dung va phat trien ung dung bao.\n- Quan ly va cap nhat cac ung dung hien co cua cong ty."
                ),
                CriteriaUi(
                    title = "Yeu cau ung vien",
                    qualification = "- Co tren 1 nam kinh nghiem ve lap trinh android (Java/Kotlin).\n- Co lam it nhat 1 du an voi kotlin",
                )
            )
        )
    ),
)

data class JobUi(
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
    val criteriaUiList: List<CriteriaUi> = emptyList(),
) : Serializable

data class CriteriaUi(
    val title: String = "",
    val qualification: String = "",
    val description: String = "",
)