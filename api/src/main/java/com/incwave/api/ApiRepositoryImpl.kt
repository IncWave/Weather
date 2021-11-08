package com.incwave.api

import android.util.Log
import com.incwave.api.model.DayForecastResponseApi
import com.incwave.api.model.GeopositionSearchResponseApi
import retrofit2.Response
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService): ApiRepository {

    companion object {
        private const val TAG = "ApiRepositoryImpl"
    }

    override suspend fun getGeopositionKey(
        apikey: String,
        latitudeLongitude: String
    ): Response<GeopositionSearchResponseApi> {
        val result = apiService.getGeopositionKey(apikey, latitudeLongitude)
        if (result.isSuccessful){
            Log.d(TAG, "getGeopositionKey:  IsSuccessful:${result.isSuccessful}  body:${result.body()}")
        } else {
            Log.d(TAG, "getGeopositionKey:  IsSuccessful:${result.isSuccessful}  code:${result.code()}" +
                    "  errorBody:${result.errorBody()} message:${result.message()}")
        }
        return result
    }

    override suspend fun getDayForecast(
        geopositionKey: String,
        apikey: String,
        details: Boolean,
        metric: Boolean
    ): Response<DayForecastResponseApi> {
        val result = apiService.getDayForecast(geopositionKey, apikey, details, metric)
        if (result.isSuccessful){
            Log.d(TAG, "getDayForecast:  IsSuccessful:${result.isSuccessful}  body:${result.body()}")
        } else {
            Log.d(TAG, "getDayForecast:  IsSuccessful:${result.isSuccessful}  code:${result.code()}" +
                    "  errorBody:${result.errorBody()} message:${result.message()}")
        }
        return result
    }
}