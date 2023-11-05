package com.android.recruitment.di

import com.android.recruitment.data.repositories.AuthRepository
import com.android.recruitment.data.repositories.AuthRepositoryImpl
import com.android.recruitment.data.repositories.JobRepository
import com.android.recruitment.data.repositories.JobRepositoryImpl
import com.android.recruitment.data.repositories.UserRepository
import com.android.recruitment.data.repositories.UserRepositoryImpl
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
    abstract fun bindResumeRepository(resumeRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindJobRepository(jobRepositoryImpl: JobRepositoryImpl): JobRepository
}