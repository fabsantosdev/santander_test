package com.fabsantosdev.pullhub.di

import com.fabsantosdev.pullhub.data.remote.api.GithubApiService
import com.fabsantosdev.pullhub.data.remote.client.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = RetrofitClient.instance

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GithubApiService =
        retrofit.create(GithubApiService::class.java)
}
