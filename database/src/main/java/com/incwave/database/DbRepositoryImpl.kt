package com.incwave.database

import com.incwave.database.dao.DayForecastDao
import com.incwave.database.model.DayForecastResponseDb
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbRepositoryImpl @Inject constructor(private val dao: DayForecastDao, private val db: AppDatabase) : DbRepository {

    override suspend fun getDayForecast(): DayForecastResponseDb? {
        if (dao.getDayForecast().isNotEmpty()){
            return dao.getDayForecast().first()
        }
        return null
    }


    override suspend fun insertDayForecast(response: DayForecastResponseDb) {
        db.clearAllTables()
        dao.insertDayForecast(response)
    }

    override suspend fun clearData() {
        db.clearAllTables()
    }
}