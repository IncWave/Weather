package com.incwave.weather.repository

import com.incwave.api.model.DayForecastResponseApi
import com.incwave.api.model.GeopositionSearchResponseApi
import com.incwave.weather.core.base.OperationInfo
import com.incwave.weather.core.model.DayForecastResponseData
import retrofit2.Response

interface DataRepository {
    suspend fun getGeopositionKey(latitudeLongitude: String): Response<GeopositionSearchResponseApi>
    suspend fun getDayForecast(geopositionKey: String): OperationInfo<DayForecastResponseData>
}