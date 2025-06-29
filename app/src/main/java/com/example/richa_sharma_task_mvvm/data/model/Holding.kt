package com.example.richa_sharma_task_mvvm.data.model

import com.google.gson.annotations.SerializedName

data class Holding(
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("ltp")
    val ltp: Double,
    @SerializedName("avgPrice")
    val averagePrice: Double,
    @SerializedName("close")
    val close: Double
)
