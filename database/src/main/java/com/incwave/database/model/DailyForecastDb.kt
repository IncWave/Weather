package com.incwave.database.model

data class DailyForecastDb(
    val date: String,
    val epochDate: Long,
    val temperature: TemperatureDb,
    val day: DayDb,
    val night: DayDb,
    val sources: List<String>,
    val mobileLink: String,
    val link: String
)