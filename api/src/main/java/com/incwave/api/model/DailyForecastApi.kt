package com.incwave.api.model

import com.google.gson.annotations.SerializedName

data class DailyForecastApi(
    @SerializedName("Date")
    val date: String,
    @SerializedName("EpochDate")
    val epochDate: Long,
    @SerializedName("Temperature")
    val temperature: TemperatureApi,
    @SerializedName("Day")
    val day: DayApi,
    @SerializedName("Night")
    val night: DayApi,
    @SerializedName("Sources")
    val sources: List<String>,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("Link")
    val link: String
)
