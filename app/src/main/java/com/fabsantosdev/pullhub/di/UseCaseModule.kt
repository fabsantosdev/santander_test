package com.fabsantosdev.pullhub.di

import com.fabsantosdev.pullhub.data.mapper.PullRequestMapper
import com.fabsantosdev.pullhub.data.mapper.RepoMapper
import com.fabsantosdev.pullhub.domain.repository.GithubRepository
import com.fabsantosdev.pullhub.domain.usecase.GetPullRequestsUseCase
import com.fabsantosdev.pullhub.domain.usecase.GetPullRequestsUseCaseImpl
import com.fabsantosdev.pullhub.domain.usecase.GetRepositoriesUseCase
import com.fabsantosdev.pullhub.domain.usecase.GetRepositoriesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetRepositoriesUseCase(
        repository: GithubRepository,
        repoMapper: RepoMapper
    ): GetRepositoriesUseCase = GetRepositoriesUseCaseImpl(repository, repoMapper)

    @Singleton
    @Provides
    fun provideGetPullRequestsUseCase(
        repository: GithubRepository,
        mapper: PullRequestMapper
    ): GetPullRequestsUseCase = GetPullRequestsUseCaseImpl(repository, mapper)
}
