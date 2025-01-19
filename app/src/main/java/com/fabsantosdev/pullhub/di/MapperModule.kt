package com.fabsantosdev.pullhub.di

import com.fabsantosdev.pullhub.data.mapper.PullRequestMapper
import com.fabsantosdev.pullhub.data.mapper.RepoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun providePullRequestMapper() = PullRequestMapper()

    @Provides
    @Singleton
    fun provideRepoMapper() = RepoMapper()
}