package com.example.twittercounter.data.remote.model


data class CreateTweetResp(
    val data: TweetData
)

data class TweetData(
    val text: String,
    val edit_history_tweet_ids: List<String>,
    val id: String
)
