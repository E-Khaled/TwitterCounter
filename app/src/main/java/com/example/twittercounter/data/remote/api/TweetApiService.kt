package com.example.twittercounter.data.remote.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TweetApiService {

    @FormUrlEncoded
    @POST("api/call")
    fun postTweet(@Field("dummy") status: String): Call<Void>
}