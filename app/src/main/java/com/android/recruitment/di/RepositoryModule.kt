package com.android.recruitment.di

import com.android.recruitment.data.repositories.AuthRepository
import com.android.recruitment.data.repositories.AuthRepositoryImpl
import com.android.recruitment.data.repositories.ResumeRepository
import com.android.recruitment.data.repositories.ResumeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindResumeRepository(resumeRepositoryImpl: ResumeRepositoryImpl): ResumeRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}