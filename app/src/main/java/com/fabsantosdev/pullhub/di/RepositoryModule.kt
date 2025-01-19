package com.fabsantosdev.pullhub.di

import com.fabsantosdev.pullhub.data.remote.api.GithubApiService
import com.fabsantosdev.pullhub.data.repository.GithubRepositoryImpl
import com.fabsantosdev.pullhub.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindGithubRepository(repositoryImpl: GithubRepositoryImpl): GithubRepository
}