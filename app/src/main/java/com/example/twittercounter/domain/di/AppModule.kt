package com.example.twittercounter.domain.di

import android.content.Context
import com.example.twittercounter.BuildConfig
import com.example.twittercounter.data.remote.api.TweetApiService
import com.example.twittercounter.data.repository.ClipboardHandlerImpl
import com.example.twittercounter.domain.repository.ClipboardHandler
import com.example.twittercounter.domain.usecase.tweet.ClearTextUseCase
import com.example.twittercounter.domain.usecase.tweet.CopyTextUseCase
import com.example.twittercounter.domain.usecase.tweet.CountCharactersUseCase
import com.example.twittercounter.domain.usecase.tweet.PostTweetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//object DataModule {
//
//
//
//}