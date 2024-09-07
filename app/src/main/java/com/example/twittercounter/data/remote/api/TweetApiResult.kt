package com.example.twittercounter.data.remote.api

sealed class TweetApiResult<out T> {
    data class Success<out T>(val data: T) : TweetApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : TweetApiResult<Nothing>()
}