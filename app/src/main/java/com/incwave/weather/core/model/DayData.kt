package com.incwave.weather.core.model

import com.incwave.api.model.DayApi
import com.incwave.database.model.DailyForecastDb
import com.incwave.database.model.DayDb

data class DayData(
    val icon: Long,
    val iconPhrase: String,
    val hasPrecipitation: Boolean,
    val precipitationType: String?,
    val precipitationIntensity: String?
) {
    fun toDb(): DayDb {
        return DayDb(this.icon, this.iconPhrase, this.hasPrecipitation, this.precipitationType,
            this.precipitationIntensity)
    }
    companion object {
        fun toData(day: DayDb): DayData {
            return DayData(day.icon, day.iconPhrase, day.hasPrecipitation, day.precipitationType, day.precipitationIntensity)
        }
        fun toData(day: DayApi): DayData {
            return DayData(day.icon, day.iconPhrase, day.hasPrecipitation, day.precipitationType, day.precipitationIntensity)
        }
    }
}
