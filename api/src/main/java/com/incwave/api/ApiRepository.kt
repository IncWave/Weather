package com.incwave.api

import com.incwave.api.model.DayForecastResponseApi
import com.incwave.api.model.GeopositionSearchResponseApi
import retrofit2.Response

interface ApiRepository {
    suspend fun getGeopositionKey(apikey: String, latitudeLongitude: String): Response<GeopositionSearchResponseApi>
    suspend fun getDayForecast(geopositionKey: String, apikey: String, details: Boolean, metric: Boolean): Response<DayForecastResponseApi>
}