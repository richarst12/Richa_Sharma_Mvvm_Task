package com.example.richa_sharma_task_mvvm.di.module

import com.example.richa_sharma_task_mvvm.domain.usecase.CalculatePortfolioResultUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideCalculatePortfolioSummaryUseCase() = CalculatePortfolioResultUseCase()
}