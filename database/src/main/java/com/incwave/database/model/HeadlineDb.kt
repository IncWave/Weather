package com.incwave.database.model

data class HeadlineDb(
    val effectiveDate: String,
    val effectiveEpochDate: Long,
    val severity: Long,
    val text: String,
    val category: String,
    val endDate: String,
    val endEpochDate: Long,
    val mobileLink: String,
    val link: String
)
