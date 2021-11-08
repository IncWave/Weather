package com.incwave.weather.ui.details_fragment

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.incwave.database.DbRepositoryImpl
import com.incwave.weather.core.base.BaseViewModel
import com.incwave.weather.core.model.DayForecastResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val dbRepository: DbRepositoryImpl
) : BaseViewModel() {

    sealed class Event {
        data class SetTimeAndWeatherOnUi(val weather: String) : Event()
    }

    private val eventChannel = Channel<DetailsViewModel.Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()


    fun setDataFromBackend() {
        launch {
            withContext(Dispatchers.Default) {
                val result: DayForecastResponseData?
                withContext(Dispatchers.Default) {
                    result = DayForecastResponseData.toData(dbRepository.getDayForecast())
                }
                if (result != null){
                    val weather = "${result.dailyForecasts[0]}"
                    eventChannel.send(Event.SetTimeAndWeatherOnUi(weather))
                }
            }
        }
    }

}