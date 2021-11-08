package com.incwave.database.model

data class DayDb(
    val icon: Long,
    val iconPhrase: String,
    val hasPrecipitation: Boolean,
    val precipitationType: String?,
    val precipitationIntensity: String?
)
