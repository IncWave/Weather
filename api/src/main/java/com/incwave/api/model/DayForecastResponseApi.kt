package com.incwave.api.model

import com.google.gson.annotations.SerializedName

data class DayForecastResponseApi(
    @SerializedName("Headline")
    val headline: HeadlineApi,

    @SerializedName("DailyForecasts")
    val dailyForecasts: List<DailyForecastApi>
)
