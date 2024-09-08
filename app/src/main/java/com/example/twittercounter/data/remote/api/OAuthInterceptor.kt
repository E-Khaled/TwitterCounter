package com.example.twittercounter.data.remote.api
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.Request
import java.io.IOException
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.apis.TwitterApi
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.oauth.OAuth10aService
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import javax.inject.Inject

class OAuth1Interceptor @Inject constructor(
    private val service: OAuth10aService,
    private val accessToken: OAuth1AccessToken
)  : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val url = originalRequest.url.toString()

        // Create OAuthRequest for signing
        val signedRequest = OAuthRequest(Verb.POST, url)

        // Sign the request using scribejava service
        service.signRequest(accessToken, signedRequest)

        // Retrieve signed headers and add them to the OkHttp request
        val requestBuilder = originalRequest.newBuilder()
        signedRequest.headers.forEach { (key, value) ->
            requestBuilder.addHeader(key, value)
        }

        return chain.proceed(requestBuilder.build())
    }
}