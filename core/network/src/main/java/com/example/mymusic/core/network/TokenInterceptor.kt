package com.example.mymusic.core.network

import com.example.mymusic.core.auth.AuthorizationManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val authorizationManager: AuthorizationManager
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Create a new request with the updated access token
        val newRequest = authorizationManager.performActionWithFreshTokens(chain.request())
        // Retry the request with the new access token
        return chain.proceed(newRequest)
    }
}