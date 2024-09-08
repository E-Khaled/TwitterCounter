package com.example.twittercounter.data.repository

import com.example.twittercounter.data.remote.api.ApiResult
import com.example.twittercounter.data.remote.api.TweetApiService
import com.example.twittercounter.data.remote.model.CreateTweetResp
import com.example.twittercounter.domain.model.CreateTweetRequest
import com.example.twittercounter.domain.repository.TweetRepository
import com.example.twittercounter.persentaion.common.NetworkUtility
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TweetRepositoryImpl @Inject constructor(
    private val apiService: TweetApiService,
    private val networkUtility: NetworkUtility
) :
    TweetRepository {
    override fun postTweet(tweetRequest: CreateTweetRequest): Flow<ApiResult<CreateTweetResp>> =
        flow {
            networkUtility.safeApiCall { apiService.postTweet(tweetRequest) }
                .collect { result -> emit(result) }
        }
}