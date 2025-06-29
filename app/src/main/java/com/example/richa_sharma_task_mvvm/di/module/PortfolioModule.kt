package com.example.richa_sharma_task_mvvm.di.module

import com.example.richa_sharma_task_mvvm.data.api.PortfolioApiService
import com.example.richa_sharma_task_mvvm.data.repository.PortfolioRepository
import com.example.richa_sharma_task_mvvm.di.qualifiers.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PortfolioModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io/"

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePortfolioApiService(retrofit: Retrofit): PortfolioApiService {
        return retrofit.create(PortfolioApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePortfolioRepository(
        portfolioApiService: PortfolioApiService
    ): PortfolioRepository {
        return PortfolioRepository(portfolioApiService)
    }
}