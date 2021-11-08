package com.incwave.weather.core.model
import com.incwave.api.model.ImumApi
import com.incwave.database.model.ImumDb

data class ImumData(
    val value: Double,
    val unit: String,
    val unitType: Long
) {
    fun toDb(): ImumDb {
        return ImumDb(this.value, this.unit, this.unitType)
    }
    companion object {
        fun toData(imum: ImumDb): ImumData {
            return ImumData(imum.value, imum.unit, imum.unitType)
        }
        fun toData(imum: ImumApi): ImumData {
            return ImumData(imum.value, imum.unit, imum.unitType)
        }
    }
}