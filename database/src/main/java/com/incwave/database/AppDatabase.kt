package com.incwave.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.incwave.database.dao.DayForecastDao
import com.incwave.database.model.DayForecastResponseDb

@Database(entities = [DayForecastResponseDb::class], version = 1)
@TypeConverters(DayForecastResponseDb.DailyForecastsConverter::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun dayForecastDao(): DayForecastDao
}