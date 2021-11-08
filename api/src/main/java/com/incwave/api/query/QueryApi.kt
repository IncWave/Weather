package com.incwave.api.query

import com.incwave.api.model.DayForecastResponseApi
import com.incwave.api.model.GeopositionSearchResponseApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QueryApi {

    @GET("/locations/v1/cities/geoposition/search")
    suspend fun getGeopositionKey(
        @Query("apikey") apikey: String,
        @Query("q") latitudeLongitude: String
    ): Response<GeopositionSearchResponseApi>

    @GET("/forecasts/v1/daily/1day/{geopositionKey}")
    suspend fun getDayForecast(
        @Path("geopositionKey") geopositionKey: String,
        @Query("apikey") apikey: String,
        @Query("details") details: Boolean,
        @Query("metric") metric: Boolean,
    ): Response<DayForecastResponseApi>
}