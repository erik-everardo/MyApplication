package com.example.myapplication.core.util

typealias SimpleDataRetrieveResult = DataRetrieveResult<Unit>

sealed class DataRetrieveResult<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null): DataRetrieveResult<T>(data)
    class Success<T>(data: T?) : DataRetrieveResult<T>(data)
    class Error<T>(message: String, data: T? = null): DataRetrieveResult<T>(data, message)
}