package com.example.twittercounter.data.di

import android.content.Context
import com.example.twittercounter.BuildConfig
import com.example.twittercounter.data.remote.api.OAuth1Interceptor
import com.example.twittercounter.data.remote.api.TweetApiService
import com.example.twittercounter.data.repository.ClipboardHandlerImpl
import com.example.twittercounter.data.repository.TweetRepositoryImpl
import com.example.twittercounter.domain.repository.ClipboardHandler
import com.example.twittercounter.domain.repository.TweetRepository
import com.example.twittercounter.domain.usecase.tweet.ClearTextUseCase
import com.example.twittercounter.domain.usecase.tweet.CopyTextUseCase
import com.example.twittercounter.domain.usecase.tweet.CountCharactersUseCase
import com.example.twittercounter.domain.usecase.tweet.PostTweetUseCase
import com.example.twittercounter.persentaion.common.Constants
import com.example.twittercounter.persentaion.common.NetworkUtility
import com.github.scribejava.apis.TwitterApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.oauth.OAuth10aService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApiService(client:OkHttpClient): TweetApiService {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TweetApiService::class.java)
    }

    @Provides
    fun provideOkHttpClient(oAuth1Interceptor:OAuth1Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(oAuth1Interceptor)
            .addInterceptor(logging)
            .build()
    }
    @Provides
    fun provideOAuth1AccessToken(): OAuth1AccessToken {
        return OAuth1AccessToken(Constants.X_ACCESS_TOKEN, Constants.X_ACCESS_TOKEN_SECRET)
    }
    @Provides
    fun provideServiceBuilder(): OAuth10aService {
        return ServiceBuilder(Constants.X_API_KEY)
            .apiSecret(Constants.X_API_SECRET_KEY)
            .build(TwitterApi.instance())
    }
    @Provides
    @Singleton
    fun provideOAuth1Interceptor(
        service: OAuth10aService,
        accessToken: OAuth1AccessToken
    ): OAuth1Interceptor {
        return OAuth1Interceptor(service, accessToken)
    }


    @Provides
    fun provideClipboardHandler(@ApplicationContext context: Context): ClipboardHandler {
        return ClipboardHandlerImpl(context)
    }

    @Provides
    fun provideCopyTextUseCase(clipboardHandler: ClipboardHandler): CopyTextUseCase {
        return CopyTextUseCase(clipboardHandler)
    }

    @Provides
    fun provideClearTextUseCase(): ClearTextUseCase {
        return ClearTextUseCase()
    }

    @Provides
    fun provideCountCharactersUseCase(): CountCharactersUseCase {
        return CountCharactersUseCase()
    }

    @Provides
    fun providePostTweetUseCase(tweetRepository: TweetRepository): PostTweetUseCase {
        return PostTweetUseCase(tweetRepository)
    }

    @Provides
    fun provideTweetRepository(
        apiService: TweetApiService,
        networkUtility: NetworkUtility
    ): TweetRepository {
        return TweetRepositoryImpl(apiService, networkUtility)
    }

    @Provides
    fun provideNetWorkUtility(@ApplicationContext context: Context): NetworkUtility {
        return NetworkUtility(context)
    }


}