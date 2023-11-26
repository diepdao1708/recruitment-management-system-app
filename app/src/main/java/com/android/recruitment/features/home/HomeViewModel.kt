package com.android.recruitment.features.home

import androidx.lifecycle.ViewModel
import com.android.recruitment.data.models.SavedAccount
import com.android.recruitment.data.repositories.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedAccount: SavedAccount,
    private val jobRepository: JobRepository,
) : ViewModel() {
}