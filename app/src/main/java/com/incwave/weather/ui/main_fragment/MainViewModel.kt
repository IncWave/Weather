package com.incwave.weather.ui.main_fragment

import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.incwave.database.DbRepositoryImpl
import com.incwave.weather.core.base.BaseViewModel
import com.incwave.weather.core.model.DayForecastResponseData
import com.incwave.weather.repository.DataRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataRepository: DataRepositoryImpl,
                                        private val dbRepository: DbRepositoryImpl): BaseViewModel() {
    var connectivityManager: ConnectivityManager? = null

    sealed class Event {
        object CheckGPSIsOn: Event()
        object OpenGPSSettings: Event()
        object GetLocation: Event()
        object CheckPermission: Event()
        object RequestPermission: Event()
        data class ShowNoInternetConnection(val isInternet: Boolean): Event()
        data class SetTimeAndWeatherOnUi(val time: String, val weather: String): Event()
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            eventChannel.send(Event.CheckGPSIsOn)
        }
    }

    fun btnGetWeatherClicked(){
        viewModelScope.launch {
            eventChannel.send(Event.CheckGPSIsOn)
        }
    }

    fun gpsIsEnabled(isEnabled: Boolean){
        viewModelScope.launch {
            if (isEnabled) {
                eventChannel.send(Event.CheckPermission)
            } else {
                eventChannel.send(Event.OpenGPSSettings)
            }
        }
    }

    fun gpsPermissionIsGrunted(isGrunted: Boolean){
        viewModelScope.launch {
            if (isGrunted) {
                eventChannel.send(Event.GetLocation)
                val result : DayForecastResponseData?
                withContext(Dispatchers.Default){
                    result = DayForecastResponseData.toData(dbRepository.getDayForecast())
                }
                if (result != null){
                    val weather = "${result.headline.text}\n\nminTemp = ${
                        result.dailyForecasts[0].temperature.minimum.value} ${result.dailyForecasts[0].temperature.minimum.unit}"
                    eventChannel.send(Event.SetTimeAndWeatherOnUi(result.headline.effectiveDate, weather))
                }
            } else {
                eventChannel.send(Event.RequestPermission)
            }
        }
    }

    private fun getGeopositionKey(latitudeLongitude: String) {
        launch {
            withContext(Dispatchers.IO) {
                val geopositionKey = dataRepository.getGeopositionKey(latitudeLongitude).body()?.geopositionKey
                if (!geopositionKey.isNullOrEmpty()) {
                    val dayForecast = dataRepository.getDayForecast(geopositionKey)
                    if (dayForecast.isSuccess && dayForecast.getResult() != null) {
                        val result = dayForecast.getResult()!!
                        withContext(Dispatchers.Default) {
                            dbRepository.insertDayForecast(result.toDb())
                        }
                        val weather = "${result.headline.text}\n\nminTemp = ${
                            result.dailyForecasts[0].temperature.minimum.value
                        } ${result.dailyForecasts[0].temperature.minimum.unit}"
                        eventChannel.send(
                            Event.SetTimeAndWeatherOnUi(
                                result.headline.effectiveDate,
                                weather
                            )
                        )
                    }
                } else {
                    Log.d("QQQ", "mainScreenViewModel getGeopositionKey was null ")
                }
            }
        }
    }


    fun latitudeLongitudeIsReceived(latitudeLongitude: String) {
        getGeopositionKey(latitudeLongitude)
    }

    private fun isNetworkConnected(): Boolean {
        Log.d("QQQ", "is network Enabled ${connectivityManager?.isActiveNetworkMetered ?: false}")
        return connectivityManager?.isActiveNetworkMetered ?: false
    }

}