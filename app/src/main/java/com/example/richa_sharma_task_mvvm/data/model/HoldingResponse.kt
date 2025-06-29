package com.example.richa_sharma_task_mvvm.data.model

import com.google.gson.annotations.SerializedName

data class HoldingResponse(
    @SerializedName("data")
    val data: HoldingData
)

data class HoldingData(
    @SerializedName("userHolding")
    val userHolding: List<Holding>
)

data class HoldingResponseDataOnly(
    val holdings: List<Holding>
)
