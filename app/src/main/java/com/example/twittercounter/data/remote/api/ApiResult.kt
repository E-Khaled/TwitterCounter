package com.example.twittercounter.data.remote.api

import com.example.twittercounter.data.remote.model.ApiError

sealed class ApiResult<out T> {
    class Loading<T : Any> : ApiResult<T>()
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error<T : Any>(val error: ApiError) : ApiResult<T>()
}