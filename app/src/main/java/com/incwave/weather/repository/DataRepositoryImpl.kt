package com.incwave.weather.repository

import com.incwave.api.ApiRepository
import com.incwave.api.model.GeopositionSearchResponseApi
import com.incwave.weather.core.base.OperationInfo
import com.incwave.weather.core.model.DayForecastResponseData
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepositoryImpl @Inject constructor(private val apiRepository: ApiRepository): DataRepository {

    companion object {
        private const val APIKEY = "rsGBHeBRIbMzenUJxBDqXmMng7v6JNHY"
    }

    override suspend fun getGeopositionKey(latitudeLongitude: String): Response<GeopositionSearchResponseApi> {
        return apiRepository.getGeopositionKey(APIKEY, latitudeLongitude)
    }

    override suspend fun getDayForecast(geopositionKey: String): OperationInfo<DayForecastResponseData> {
        val result = apiRepository.getDayForecast(geopositionKey, APIKEY, details = true, metric = true)
        return if (result.isSuccessful && result.body() != null) {
            OperationInfo(
                result.isSuccessful,
                OperationInfo.ErrorInfo(null, result.errorBody().toString(),
                    result.code()),DayForecastResponseData.toData(result.body()!!))
        } else {
            OperationInfo(
                result.isSuccessful,
                OperationInfo.ErrorInfo(null, result.errorBody().toString(),
                    result.code()),DayForecastResponseData.toData(result.body()!!))
        }
    }

}