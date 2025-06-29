package com.example.richa_sharma_task_mvvm.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richa_sharma_task_mvvm.data.repository.PortfolioRepository
import com.example.richa_sharma_task_mvvm.domain.usecase.CalculatePortfolioResultUseCase
import com.example.richa_sharma_task_mvvm.presentation.HoldingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val repository: PortfolioRepository,
    private val calculateSummaryUseCase: CalculatePortfolioResultUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "HoldingsViewModel"
    }

    private val _uiState = MutableStateFlow<HoldingsUiState>(HoldingsUiState.Loading)
    val uiState: StateFlow<HoldingsUiState> = _uiState

    init {
        fetchHoldings()
    }

    fun fetchHoldings() {
        _uiState.value = HoldingsUiState.Loading
        viewModelScope.launch {
            repository.fetchHoldings().onSuccess { response ->
                val holdings = response.holdings
                val summary = calculateSummaryUseCase(holdings)

                _uiState.value = HoldingsUiState.Success(
                    holdings = holdings,
                    summary = summary
                )

                Log.d(TAG, "Successfully fetched holdings: $holdings")

            }.onFailure { ex ->
                if (ex is IOException) {
                    _uiState.value = HoldingsUiState.Error("No Internet Connection")
                } else {
                    _uiState.value = HoldingsUiState.Error(ex.message ?: "Unknown error")
                }
                Log.e(TAG, "Failed to fetch holdings: ${ex.localizedMessage}", ex)
            }
        }
    }
}
