package com.incwave.weather.core.model

import com.incwave.api.model.DayForecastResponseApi
import com.incwave.api.model.HeadlineApi
import com.incwave.database.model.HeadlineDb

data class HeadlineData(
    val effectiveDate: String,
    val effectiveEpochDate: Long,
    val severity: Long,
    val text: String,
    val category: String,
    val endDate: String,
    val endEpochDate: Long,
    val mobileLink: String,
    val link: String
) {
    fun toDb(): HeadlineDb {
        return HeadlineDb(this.effectiveDate, this.effectiveEpochDate, this.severity, this.text,
            this.category, this.endDate, this.endEpochDate, this.mobileLink, this.link)
    }
    companion object {
        fun toData(hl: HeadlineDb): HeadlineData{
            return HeadlineData(hl.effectiveDate, hl.effectiveEpochDate,hl.severity, hl.text,
                hl.category, hl.endDate, hl.endEpochDate, hl.mobileLink, hl.link)
        }
        fun toData(hl: HeadlineApi): HeadlineData{
            return HeadlineData(hl.effectiveDate, hl.effectiveEpochDate,hl.severity, hl.text,
                hl.category, hl.endDate, hl.endEpochDate, hl.mobileLink, hl.link)
        }
    }
}
