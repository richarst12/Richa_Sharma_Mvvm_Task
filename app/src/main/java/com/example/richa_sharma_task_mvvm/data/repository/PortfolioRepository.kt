package com.example.richa_sharma_task_mvvm.data.repository

import android.util.Log
import com.example.richa_sharma_task_mvvm.data.api.PortfolioApiService
import com.example.richa_sharma_task_mvvm.data.model.HoldingResponseData
import javax.inject.Inject

class PortfolioRepository @Inject constructor(
    private val apiService: PortfolioApiService
) {
    companion object {
        private const val TAG = "PortfolioRepository"
    }

    suspend fun fetchHoldings(): Result<HoldingResponseData> {
        return try {
            val response = apiService.getHoldings()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val holdings = body.data.userHolding
                Log.d(TAG, "Successfully fetched holdings: $holdings")
                Result.success(HoldingResponseData(holdings))
            } else {
                val errorMessage =
                    response.message().takeIf { it.isNotBlank() } ?: "Unknown API error"
                Result.failure(Exception("API Error: $errorMessage"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception while fetching holdings: ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }
}