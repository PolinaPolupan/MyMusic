package com.example.mymusic.core.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import com.example.mymusic.core.common.ApplicationScope
import com.example.mymusic.core.common.Constants
import com.example.mymusic.core.common.IoDispatcher
import com.example.mymusic.core.datastore.MyMusicPreferencesDataSource
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private val userDataSource: MyMusicPreferencesDataSource,
    @ApplicationContext val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val coroutineScope: CoroutineScope
) {
    // Variables which are used for managing the auth flow
    private lateinit var _authorizationService: AuthorizationService
    private lateinit var _authServiceConfig: AuthorizationServiceConfiguration
    private var _authState: AuthState = AuthState()

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
        if (jsonString != null && !TextUtils.isEmpty(jsonString) ) {
            try {
                _authState = AuthState.jsonDeserialize(jsonString)
                Log.i("MainActivity", "Access token: " + _authState.accessToken)
                makeApiCall()
                if (_authState.accessToken.isNullOrEmpty()) {
                    Log.e("MainActivity", "Access token is null: Init empty auth state")
                    persistState("") // Init to the empty auth state. User will be redirected to the login screen
                }
            } catch(jsonException: JSONException) {
                Log.d("MainActivity", "JSON exception: Init empty auth state")
                persistState("") // Init to the empty auth state. User will be redirected to the login screen
            }
        } else {
            Log.d("MainActivity", "Auth state is null: Init empty auth state")
            persistState("") // Init to the empty auth state. User will be redirected to the login screen
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
            Constants.SCOPE_USER_READ_PRIVATE,
            Constants.SCOPE_USER_READ_RECENTLY_PLAYED,
            Constants.SCOPE_USER_LIBRARY_READ,
            Constants.SCOPE_PLAYLIST_READ_PRIVATE,
            Constants.SCOPE_USER_TOP_READ,
            Constants.SCOPE_USER_READ_PLAYBACK_STATE
        )

        val request = builder.build()

        return _authorizationService.getAuthorizationRequestIntent(request)
    }

    fun handleAuthorizationResponse(intent: Intent, onSuccess: () -> Unit) {
        val authorizationResponse: AuthorizationResponse? = AuthorizationResponse.fromIntent(intent)
        val error = AuthorizationException.fromIntent(intent)

        _authState = AuthState(authorizationResponse, error)

        val tokenExchangeRequest = authorizationResponse?.createTokenExchangeRequest()

        if (tokenExchangeRequest != null) {
            _authorizationService.performTokenRequest(tokenExchangeRequest) { response, exception ->
                if (exception != null) {
                    Log.e("MainActivity", exception.errorDescription.toString())
                    _authState = AuthState()
                    persistState("")
                } else {
                    if (response != null) {
                        _authState.update(response, exception)
                        makeApiCall()
                        persistState(_authState.jsonSerializeString())
                        onSuccess()
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
                persistState(_authState.jsonSerializeString())
                newRequest = request.newBuilder()
                    .header("Authorization", "Bearer " + _authState.accessToken)
                    .build()
            } else {
                Log.e("MainActivity", "Error token response: ${ex.error} ${ex.errorDescription}, message: ${ex.message}")
                persistState("")
            }
        }
        return newRequest
    }

    private fun makeApiCall() {
        _authState.performActionWithFreshTokens(_authorizationService
        ) { _, _, _ ->
            coroutineScope.launch {
                withContext(dispatcher) {
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url("https://api.spotify.com/v1/me")
                        .addHeader("Authorization", "Bearer " + _authState.accessToken)
                        .build()

                    try {
                        val response = client.newCall(request).execute()
                        val jsonBody = response.body()?.string() ?: ""

                        val user: SpotifyUser = Json.decodeFromString(jsonBody)
                        updateUserData(user)
                        persistState(_authState.jsonSerializeString())

                    } catch (e: Exception) {
                        Log.e("MainActivity", "API call error! " + e.message)
                        _authState = AuthState()
                        persistState("") // Init to the empty auth state. User will be redirected to the login screen
                    }
                }
            }
        }
    }

    private fun persistState(authState: String) {
        coroutineScope.launch {
            withContext(dispatcher) {
                userDataSource.updateAuthState(authState)
            }
        }
    }

    private fun updateUserData(user: SpotifyUser) {
        coroutineScope.launch {
            withContext(dispatcher) {
                userDataSource.updateUserData(
                    displayName = user.displayName,
                    email = user.email,
                    imageUrl = if (user.images.isNotEmpty()) user.images[0].url else ""
                )
            }
        }
    }

    /**
     * [resetAuthState] sets auth state to the empty state. Primarily is used for testing auth flow
     */
    private fun resetAuthState() {
        _authState = AuthState()
    }
}