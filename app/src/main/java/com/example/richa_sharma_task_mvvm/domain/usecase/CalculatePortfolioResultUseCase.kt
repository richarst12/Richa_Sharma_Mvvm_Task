package com.example.richa_sharma_task_mvvm.domain.usecase

import com.example.richa_sharma_task_mvvm.data.model.Holding

data class PortfolioSummary(
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPnL: Double,
    val todayPnLValue: Double
)

class CalculatePortfolioResultUseCase {

    operator fun invoke(holdings: List<Holding>): PortfolioSummary {
        val currentValue = holdings.sumOf { it.ltp * it.quantity }
        val totalInvestment = holdings.sumOf { it.averagePrice * it.quantity }
        val totalPnL = currentValue - totalInvestment
        val todayPnLValue = holdings.sumOf { (it.close - it.ltp) * it.quantity }

        return PortfolioSummary(
            currentValue = currentValue,
            totalInvestment = totalInvestment,
            totalPnL = totalPnL,
            todayPnLValue = todayPnLValue
        )
    }
}