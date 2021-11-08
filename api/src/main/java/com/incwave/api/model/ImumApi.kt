package com.incwave.api.model

import com.google.gson.annotations.SerializedName

data class ImumApi(
    @SerializedName("Value")
    val value: Double,
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Long
)