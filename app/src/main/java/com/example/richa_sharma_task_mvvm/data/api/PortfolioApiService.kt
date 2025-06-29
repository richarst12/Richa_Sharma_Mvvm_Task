package com.example.richa_sharma_task_mvvm.data.api

import com.example.richa_sharma_task_mvvm.data.model.HoldingResponse
import retrofit2.Response
import retrofit2.http.GET

interface PortfolioApiService {
    @GET("/")
    suspend fun getHoldings(): Response<HoldingResponse>
}