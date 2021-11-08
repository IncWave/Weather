package com.incwave.api.model

import com.google.gson.annotations.SerializedName

data class HeadlineApi(
    @SerializedName("EffectiveDate")
    val effectiveDate: String,
    @SerializedName("EffectiveEpochDate")
    val effectiveEpochDate: Long,
    @SerializedName("Severity")
    val severity: Long,
    @SerializedName("Text")
    val text: String,
    @SerializedName("Category")
    val category: String,
    @SerializedName("EndDate")
    val endDate: String,
    @SerializedName("EndEpochDate")
    val endEpochDate: Long,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("Link")
    val link: String
)
