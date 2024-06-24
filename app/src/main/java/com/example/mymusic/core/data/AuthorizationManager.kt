package com.example.mymusic.core.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import androidx.hilt.navigation.compose.R
import com.example.mymusic.Constants
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.openid.appauth.AppAuthConfiguration
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.browser.BrowserAllowList
import net.openid.appauth.browser.VersionedBrowserMatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import java.security.MessageDigest
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationManager @Inject constructor(
    private val userDataRepository: UserDataRepository,
    @ApplicationContext val context: Context
) {
    // Variables which are used for managing the auth flow
    private lateinit var _authorizationService: AuthorizationService
    private lateinit var _authServiceConfig: AuthorizationServiceConfiguration
    private lateinit var _authState: AuthState

    init {
        initAuthServiceConfig()
        initAuthService()
    }

    private fun initAuthServiceConfig() {
        _authServiceConfig = AuthorizationServiceConfiguration(
            Uri.parse(Constants.URL_AUTHORIZATION),
            Uri.parse(Constants.URL_TOKEN_EXCHANGE),
            null,
            Uri.parse(Constants.URL_AUTH_REDIRECT))
    }

    private fun initAuthService() {
        val appAuthConfiguration = AppAuthConfiguration.Builder()
            .setBrowserMatcher(
                BrowserAllowList(
                    VersionedBrowserMatcher.CHROME_CUSTOM_TAB,
                    VersionedBrowserMatcher.SAMSUNG_CUSTOM_TAB
                )
            ).build()

        _authorizationService = AuthorizationService(
            Contexts.getApplication(context),
            appAuthConfiguration)
    }

    fun restoreState(jsonString: String?) {
        if(jsonString != null && !TextUtils.isEmpty(jsonString) ) {
            try {
                _authState = AuthState.jsonDeserialize(jsonString)
                Log.i("MainActivity", "Access token: " + _authState.accessToken.toString())
            } catch(jsonException: JSONException) {
                Log.d("MainActivity", "Failed to load auth state")
            }
        }
    }

    fun signIn(): Intent {
        val secureRandom = SecureRandom()
        val bytes = ByteArray(64)
        secureRandom.nextBytes(bytes)

        val encoding = Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP
        val codeVerifier = Base64.encodeToString(bytes, encoding)

        val digest = MessageDigest.getInstance(Constants.MESSAGE_DIGEST_ALGORITHM)
        val hash = digest.digest(codeVerifier.toByteArray())
        val codeChallenge = Base64.encodeToString(hash, encoding)

        val builder = AuthorizationRequest.Builder(
            _authServiceConfig,
            Constants.CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse(Constants.URL_AUTH_REDIRECT))
            .setCodeVerifier(codeVerifier,
                codeChallenge,
                Constants.CODE_VERIFIER_CHALLENGE_METHOD)

        builder.setScopes(
            Constants.SCOPE_STREAMING,
            Constants.SCOPE_EMAIL,
            Constants.SCOPE_APP_REMOTE_CONTROL,
            Constants.SCOPE_USER_MODIFY_PLAYBACK_STATE,
            Constants.SCOPE_USER_READ_PRIVATE)

        val request = builder.build()

        return _authorizationService.getAuthorizationRequestIntent(request)
    }

    fun handleAuthorizationResponse(intent: Intent, coroutineScope: CoroutineScope) {
        val authorizationResponse: AuthorizationResponse? = AuthorizationResponse.fromIntent(intent)
        val error = AuthorizationException.fromIntent(intent)

        _authState = AuthState(authorizationResponse, error)

        val tokenExchangeRequest = authorizationResponse?.createTokenExchangeRequest()

        if (tokenExchangeRequest != null) {
            _authorizationService.performTokenRequest(tokenExchangeRequest) { response, exception ->
                if (exception != null) {
                    _authState = AuthState()
                } else {
                    if (response != null) {
                        _authState.update(response, exception)
                        makeApiCall(coroutineScope)
                        persistState(_authState.jsonSerializeString(), coroutineScope)
                    }
                }
            }
        }
    }

    fun performActionWithFreshTokens(request: Request): Request {
        var newRequest: Request = request
        _authState.performActionWithFreshTokens(_authorizationService
        ) { _, _, ex ->
            Log.i("MainActivity", "Access token:" + _authState.accessToken)
            if (ex == null) {
                newRequest = request.newBuilder()
                    .header("Authorization", "Bearer " + _authState.accessToken)
                    .build()
            }
        }
        return newRequest
    }

    private fun makeApiCall(coroutineScope: CoroutineScope) {
        _authState.performActionWithFreshTokens(_authorizationService
        ) { _, _, _ ->
            coroutineScope.launch {
                async(Dispatchers.IO) {
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url("https://api.spotify.com/v1/me")
                        .addHeader("Authorization", "Bearer " + _authState.accessToken)
                        .build()

                    try {
                        val response = client.newCall(request).execute()
                        val jsonBody = response.body?.string() ?: ""

                        val user: User = Json.decodeFromString(jsonBody)
                        updateUserData(user, coroutineScope)
                        if (user.images.isNotEmpty()) updateUserImageUrl(user.images[0].url, coroutineScope) else updateUserImageUrl("", coroutineScope)

                    } catch (e: Exception) {
                        Log.e("MainActivity", "API call error!")
                    }
                }
            }
        }
    }

    private fun persistState(authState: String, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            userDataRepository.updateAuthState(authState)
        }
    }

    private fun updateUserData(user: User, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            userDataRepository.updateUserData(
                displayName = user.displayName,
                email = user.email
            )
        }
    }

    private fun updateUserImageUrl(imageUrl: String, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            userDataRepository.updateImageUrl(
                imageUrl = imageUrl
            )
        }
    }
}