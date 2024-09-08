package com.example.twittercounter.domain.usecase.tweet

import com.example.twittercounter.data.remote.api.ApiResult
import com.example.twittercounter.data.remote.model.CreateTweetResp
import com.example.twittercounter.domain.model.CreateTweetRequest
import com.example.twittercounter.domain.repository.TweetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostTweetUseCase @Inject constructor(private val repository: TweetRepository) {
    fun execute(tweet: String): Flow<ApiResult<CreateTweetResp>> {
        return repository.postTweet(CreateTweetRequest(tweet))
    }

}