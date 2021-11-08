package com.incwave.api

import com.incwave.api.model.DayForecastResponseApi
import com.incwave.api.model.GeopositionSearchResponseApi
import com.incwave.api.query.QueryApi
import retrofit2.Response
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val queryApi: QueryApi): ApiService{
    companion object {
        private const val TAG = "ApiServiceImpl"
    }

    override suspend fun getGeopositionKey(
        apikey: String,
        latitudeLongitude: String
    ): Response<GeopositionSearchResponseApi> {
        return queryApi.getGeopositionKey(apikey, latitudeLongitude)
    }

    override suspend fun getDayForecast(
        geopositionKey: String,
        apikey: String,
        details: Boolean,
        metric: Boolean
    ): Response<DayForecastResponseApi> {
        return queryApi.getDayForecast(geopositionKey, apikey, details, metric)
    }
}