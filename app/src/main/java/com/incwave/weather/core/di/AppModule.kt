package com.incwave.weather.core.di

import android.content.Context
import androidx.room.Room
import com.incwave.api.ApiRepository
import com.incwave.api.ApiRepositoryImpl
import com.incwave.api.ApiService
import com.incwave.api.ApiServiceImpl
import com.incwave.api.query.QueryApi
import com.incwave.database.AppDatabase
import com.incwave.database.DbRepository
import com.incwave.database.DbRepositoryImpl
import com.incwave.database.dao.DayForecastDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl(): String = "https://dataservice.accuweather.com/"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideQueryApi(retrofit: Retrofit): QueryApi = retrofit.create(QueryApi::class.java)

    @Provides
    @Singleton
    fun provideApiService(queryApi: QueryApi): ApiService = ApiServiceImpl(queryApi)

    @Provides
    @Singleton
    fun provideApiRepository(apiService: ApiService): ApiRepository = ApiRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideDbRepository(dao: DayForecastDao, db: AppDatabase): DbRepository = DbRepositoryImpl(dao, db)

    @Provides
    fun provideDayForecastDao(appDatabase: AppDatabase): DayForecastDao {
        return appDatabase.dayForecastDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, "DayForecast").build()
    }
}