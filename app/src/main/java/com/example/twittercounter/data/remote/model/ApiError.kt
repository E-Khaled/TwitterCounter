package com.example.twittercounter.data.remote.model

//data class ApiError(val code: Int, val message: String)
//data class ApiError(
//    val detail: String,
//    val type: String,
//    val title: String,
//    val status: Int
//)
data class ApiError(
    val status: Int,
    val detail: String,
)