package com.example.richa_sharma_task_mvvm

import app.cash.turbine.test
import com.example.richa_sharma_task_mvvm.data.model.Holding
import com.example.richa_sharma_task_mvvm.data.model.HoldingResponseData
import com.example.richa_sharma_task_mvvm.data.repository.PortfolioRepository
import com.example.richa_sharma_task_mvvm.domain.usecase.CalculatePortfolioResultUseCase
import com.example.richa_sharma_task_mvvm.domain.usecase.PortfolioSummary
import com.example.richa_sharma_task_mvvm.presentation.HoldingsUiState
import com.example.richa_sharma_task_mvvm.presentation.viewmodel.HoldingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: PortfolioRepository
    private lateinit var useCase: CalculatePortfolioResultUseCase
    private lateinit var viewModel: HoldingsViewModel

    @Before
    fun setup() {
        repository = mock(PortfolioRepository::class.java)
        useCase = mock(CalculatePortfolioResultUseCase::class.java)
    }

    @Test
    fun `fetchHoldings emits Success when result returns data`() = runTest {
        // Given
        val holdings = listOf(Holding("Test", 11, 101.0, 90.0, 101.0))
        val resultSummary = PortfolioSummary(1010.0, 900.0, 600.0, 40.0)

        // When
        whenever(repository.fetchHoldings()).thenReturn(
            Result.success(HoldingResponseData(holdings))
        )
        whenever(useCase.invoke(any())).thenReturn(resultSummary)

        viewModel = HoldingsViewModel(repository, useCase)

        viewModel.uiState.test {
            assertEquals(HoldingsUiState.Loading, awaitItem())

            val successState = awaitItem() as HoldingsUiState.Success
            assertEquals(holdings, successState.holdings)
            assertEquals(resultSummary, successState.summary)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchHoldings emits Error`() = runTest {
        //when
        whenever(repository.fetchHoldings()).thenReturn(Result.failure(Exception("Error")))
        viewModel = HoldingsViewModel(repository, useCase)
        viewModel.uiState.test {
            assertEquals(HoldingsUiState.Loading, awaitItem())
            val errorState = awaitItem() as HoldingsUiState.Error
            assertEquals("Error", errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}