package com.incwave.database

import com.incwave.database.model.DayForecastResponseDb


interface DbRepository {
    suspend fun getDayForecast(): DayForecastResponseDb?
    suspend fun insertDayForecast(response: DayForecastResponseDb)
    suspend fun clearData()
}