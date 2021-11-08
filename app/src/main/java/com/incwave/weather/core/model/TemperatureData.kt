package com.incwave.weather.core.model

import com.incwave.api.model.TemperatureApi
import com.incwave.database.model.TemperatureDb

data class TemperatureData(
    val minimum: ImumData,
    val maximum: ImumData
) {
    fun toDb(): TemperatureDb {
        return TemperatureDb(this.minimum.toDb(), this.maximum.toDb())
    }
    companion object {
        fun toData(temp: TemperatureDb): TemperatureData {
            return TemperatureData(ImumData.toData(temp.minimum), ImumData.toData(temp.maximum))
        }
        fun toData(temp: TemperatureApi): TemperatureData {
            return TemperatureData(ImumData.toData(temp.minimum), ImumData.toData(temp.maximum))
        }
    }
}
