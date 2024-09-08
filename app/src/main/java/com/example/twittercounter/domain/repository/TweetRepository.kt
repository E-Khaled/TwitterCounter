package com.example.twittercounter.domain.repository

import com.example.twittercounter.data.remote.api.ApiResult
import com.example.twittercounter.data.remote.model.CreateTweetResp
import com.example.twittercounter.domain.model.CreateTweetRequest
import kotlinx.coroutines.flow.Flow

interface TweetRepository {
    fun postTweet(tweetRequest: CreateTweetRequest): Flow<ApiResult<CreateTweetResp>>
}