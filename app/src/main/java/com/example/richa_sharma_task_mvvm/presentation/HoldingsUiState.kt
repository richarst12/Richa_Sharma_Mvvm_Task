package com.example.richa_sharma_task_mvvm.presentation

import com.example.richa_sharma_task_mvvm.data.model.Holding
import com.example.richa_sharma_task_mvvm.domain.usecase.PortfolioSummary

sealed class HoldingsUiState {
    object Loading : HoldingsUiState()
    data class Success(
        val holdings: List<Holding>,
        val summary: PortfolioSummary
    ) : HoldingsUiState()
    data class Error(val message: String) : HoldingsUiState()
}