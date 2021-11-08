package com.incwave.weather.core.base

open class OperationInfo<D>(val isSuccess: Boolean = false, val error: ErrorInfo? = null, private val response: D? = null) {
    data class ErrorInfo(val t: Throwable?, val errorMessage: String?, val errorCode: Int?)

    fun getResult(): D? = response
}