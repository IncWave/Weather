package com.incwave.api.model

import com.google.gson.annotations.SerializedName

data class TemperatureApi(
    @SerializedName("Minimum")
    val minimum: ImumApi,
    @SerializedName("Maximum")
    val maximum: ImumApi
)
