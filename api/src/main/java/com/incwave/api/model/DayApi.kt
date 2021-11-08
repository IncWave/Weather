package com.incwave.api.model

import com.google.gson.annotations.SerializedName

data class DayApi(
    @SerializedName("Icon")
    val icon: Long,
    @SerializedName("IconPhrase")
    val iconPhrase: String,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("PrecipitationType")
    val precipitationType: String,
    @SerializedName("PrecipitationIntensity")
    val precipitationIntensity: String
)
