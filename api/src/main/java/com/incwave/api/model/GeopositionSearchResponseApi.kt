package com.incwave.api.model

import com.google.gson.annotations.SerializedName

data class GeopositionSearchResponseApi(
    @SerializedName("Key")
    val geopositionKey: String,
)