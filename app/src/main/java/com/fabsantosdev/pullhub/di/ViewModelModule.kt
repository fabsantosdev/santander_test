package com.fabsantosdev.pullhub.di

import com.fabsantosdev.pullhub.domain.usecase.GetPullRequestsUseCase
import com.fabsantosdev.pullhub.domain.usecase.GetRepositoriesUseCase
import com.fabsantosdev.pullhub.presentation.screens.home.HomeViewModel
import com.fabsantosdev.pullhub.presentation.screens.repodetails.PullRequestViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideHomeViewModel(
        useCase: GetRepositoriesUseCase
    ): HomeViewModel = HomeViewModel(useCase)

    @Provides
    fun providePullRequestViewModel(
        useCase: GetPullRequestsUseCase
    ): PullRequestViewModel = PullRequestViewModel(useCase)
}