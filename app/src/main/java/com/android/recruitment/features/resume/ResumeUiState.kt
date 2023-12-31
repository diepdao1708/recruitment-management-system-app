package com.android.recruitment.features.resume

import java.io.Serializable

data class ResumeUiState(
    val resumeList: List<ItemResume> = emptyList(),
    val message: String = "",
)

data class ItemResume(
    val name: String = "",
    val path: String = "",
) : Serializable