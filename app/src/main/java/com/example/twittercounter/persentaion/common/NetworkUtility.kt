package com.example.twittercounter.persentaion.common

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.twittercounter.R
import com.example.twittercounter.data.remote.api.ApiResult
import com.example.twittercounter.data.remote.model.ApiError
import com.example.twittercounter.persentaion.common.Constants.LANG_EN
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import javax.inject.Inject

class NetworkUtility @Inject constructor(
    private val applicationContext: Context,
) {
    private fun isOnline(): Boolean {

        val connectivityManager: ConnectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) != null
    }

    fun <T : Any> safeApiCall(
        apiToBeCalled: suspend () -> T
    ): Flow<ApiResult<T>> = flow {
        try {
            if (!isOnline()) {
                emit(
                    ApiResult.Error(
                        ApiError(
                            NetworkError.NO_INTERNET.code,
                            applicationContext.getLocalizedResources(LANG_EN)
                                .getString(R.string.msg_no_network)
                        )
                    )
                )
            } else {
                emit(ApiResult.Loading())
                val response = apiToBeCalled.invoke()
                emit(
                    ApiResult.Success(response)
                )
            }
        } catch (ex: Exception) {
            handleNetworkException(ex)?.let {
                emit(ApiResult.Error(it))
            }
        }
    }


    private fun handleNetworkException(ex: Exception): ApiError? {
        if (ex is HttpException) {
            return when (ex.code()) {
                //TODO Handle unAuthorized scenario
                NetworkError.UNAUTHORIZED.code -> {
                    ApiError(
                        ex.code(),
                        applicationContext.getLocalizedResources(LANG_EN)
                            .getString(R.string.unAuthorized_msg)
                    )
                }

                else -> {
                    ApiError(ex.code(), getErrorMessage(ex.code(), ex.response()?.errorBody()))
                    /*TODO maybe make a map for error msgs if docs found*/
                }
            }
        } else {
            return ApiError(
                NetworkError.UNKNOWN_ERROR.code,
                applicationContext.getLocalizedResources(LANG_EN)
                    .getString(R.string.msg_can_not_get_data)
            )
        }
    }

    private fun getErrorMessage(responseCode: Int, responseBody: ResponseBody?): String {
        val resources = applicationContext.getLocalizedResources(LANG_EN)

        return getBackendErrorMessage(responseBody) ?: resources.getString(R.string.msg_bad_request)
    }
}

private fun getBackendErrorMessage(responseBody: ResponseBody?): String? {
    val gson = Gson()
    val type = object : TypeToken<ApiError>() {}.type
    val errorResponse: ApiError? = gson.fromJson(responseBody?.charStream(), type)

    return errorResponse?.detail
}


enum class NetworkError(val code: Int) {
    NO_INTERNET(1000),
    UNKNOWN_ERROR(3000),
    DATA_ERROR(4000),

    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    TIMEOUT(408),
    CONFLICT(409),
    TOO_MANY_REQUESTS(429),
    INTERNAL_SERVER_ERROR(500),
}