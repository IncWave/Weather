package com.incwave.weather.core.model

import com.incwave.api.model.DayForecastResponseApi
import com.incwave.database.model.DayForecastResponseDb

data class DayForecastResponseData(
    val headline: HeadlineData,
    val dailyForecasts: List<DailyForecastData>
) {
    fun toDb(): DayForecastResponseDb {
        return DayForecastResponseDb(0, this.headline.toDb(), this.dailyForecasts.map {
            it.toDb()
        })
    }
    companion object {
        fun toData(day: DayForecastResponseDb?): DayForecastResponseData? {
            return if (day != null){
                DayForecastResponseData(HeadlineData.toData(day.headline), day.dailyForecasts.map {
                    DailyForecastData.toData(it)
                })
            } else {
                null
            }
        }
        fun toData(day: DayForecastResponseApi): DayForecastResponseData {
            return DayForecastResponseData(HeadlineData.toData(day.headline), day.dailyForecasts.map {
                DailyForecastData.toData(it)
            })
        }
    }
}
