package com.example.mymusic.core.data.network

import android.util.Log
import com.example.mymusic.core.data.AuthorizationManager
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val authorizationManager: AuthorizationManager
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Create a new request with the updated access token
        val newRequest = authorizationManager.performActionWithFreshTokens(chain.request())
        // Retry the request with the new access token
        val  response = chain.proceed(newRequest)
        if (response.code == HttpURLConnection.HTTP_OK) {
            //response success
            Log.d("MainActivity", response.body!!.string())
        } else {
            //handle your other response codes here
            Log.e("MainActivity", response.code.toString())
        }
        return response
    }
}