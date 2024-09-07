package com.example.twittercounter

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.twittercounter.data.remote.api.TweetApiService
import com.example.twittercounter.data.repository.TweetRepositoryImpl
import com.example.twittercounter.domain.repository.TweetRepository
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}