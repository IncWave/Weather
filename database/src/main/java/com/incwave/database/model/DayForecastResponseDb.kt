package com.incwave.database.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "forecast")
data class DayForecastResponseDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @Embedded val headline: HeadlineDb,
    @ColumnInfo(name = "forecast")
    @TypeConverters(DailyForecastsConverter::class)
    val dailyForecasts: List<DailyForecastDb>
) {
    class DailyForecastsConverter{
        @TypeConverter
        fun fromList(forecasts: List<DailyForecastDb>): String{
            return Gson().toJson(forecasts, object : TypeToken<List<DailyForecastDb>>() {}.type)
        }

        @TypeConverter
        fun toList(forecasts: String): List<DailyForecastDb> {
            return Gson().fromJson(forecasts, object : TypeToken<List<DailyForecastDb>>() {}.type)
        }
    }
}
