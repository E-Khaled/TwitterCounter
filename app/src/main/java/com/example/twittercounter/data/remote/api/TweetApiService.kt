package com.example.twittercounter.data.remote.api

import com.example.twittercounter.data.remote.model.CreateTweetResp
import com.example.twittercounter.domain.model.CreateTweetRequest
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TweetApiService {

    //according to https://documenter.getpostman.com/view/9956214/T1LMiT5U#5bd6ebb1-9d79-4456-a9a6-22ead4a41625
    //https://api.twitter.com/2/tweets
    //headers are consumer-key,consumer-secret,token,token-secret
//    https://api.twitter.com
    @POST("/2/tweets")
    suspend fun postTweet(@Body createTweetRequest: CreateTweetRequest): CreateTweetResp

}