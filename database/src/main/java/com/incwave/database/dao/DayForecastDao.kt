package com.incwave.database.dao

import androidx.room.*
import com.incwave.database.model.DayForecastResponseDb

@Dao
interface DayForecastDao {

    @Insert
    fun insertDayForecast(dayForecastResponse: DayForecastResponseDb)

    @Query("SELECT * FROM forecast LIMIT 1")
    fun getDayForecast(): List<DayForecastResponseDb>
}