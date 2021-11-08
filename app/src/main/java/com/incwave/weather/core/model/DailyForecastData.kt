package com.incwave.weather.core.model

import com.incwave.api.model.DailyForecastApi
import com.incwave.database.model.DailyForecastDb

data class DailyForecastData(
    val date: String,
    val epochDate: Long,
    val temperature: TemperatureData,
    val day: DayData,
    val night: DayData,
    val sources: List<String>,
    val mobileLink: String,
    val link: String
) {
    fun toDb(): DailyForecastDb {
        return DailyForecastDb(this.date, this.epochDate, this.temperature.toDb(), this.day.toDb(),
            this.night.toDb(), this.sources, this.mobileLink, this.link)
    }
    companion object {
        fun toData(df: DailyForecastDb): DailyForecastData{
            return DailyForecastData(df.date, df.epochDate, TemperatureData.toData(df.temperature), DayData.toData(df.day),
                DayData.toData(df.night), df.sources, df.mobileLink, df.link)
        }
        fun toData(df: DailyForecastApi): DailyForecastData{
            return DailyForecastData(df.date, df.epochDate, TemperatureData.toData(df.temperature), DayData.toData(df.day),
                DayData.toData(df.night), df.sources, df.mobileLink, df.link)
        }
    }
}
