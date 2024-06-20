package com.example.mymusic.feature.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.mymusic.Constants
import com.example.mymusic.MainActivityUiState
import com.example.mymusic.core.data.UserDataRepository
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
import org.json.JSONObject
import java.security.MessageDigest
import java.security.SecureRandom
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    @ApplicationContext val context: Context
): ViewModel() {

    private lateinit var _authorizationService: AuthorizationService
    private lateinit var _authServiceConfig: AuthorizationServiceConfiguration
    private lateinit var _authState: AuthState

    var authState: StateFlow<String?> = userDataRepository.userPreferencesFlow
        .map {
            it.authState
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), null)

    init {
        initAuthServiceConfig()
        initAuthService()
        restoreState()
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
            getApplication(context),
            appAuthConfiguration)
    }

    fun attemptAuthorization() : Intent {
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

    fun handleAuthorizationResponse(intent: Intent) {
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
                        response.accessToken?.let { Log.d("MainActivity", it) }
                        _authState.update(response, exception)
                        makeApiCall()
                    }
                }
            }
            persistState(_authState.jsonSerializeString())
            makeApiCall()
        }
    }

    fun makeApiCall() {
        _authState.performActionWithFreshTokens(_authorizationService,
            object: AuthState.AuthStateAction {
                override fun execute(accessToken: String?, idToken: String?, ex: AuthorizationException?) {
                    viewModelScope.launch {
                        async(Dispatchers.IO) {
                            _authState.accessToken?.let { Log.d("MainActivity", it) }
                            val client = OkHttpClient()
                            val request = Request.Builder()
                                .url("https://api.spotify.com/v1/me")
                                .addHeader("Authorization", "Bearer " + _authState.accessToken)
                                .build()

                            try {
                                val response = client.newCall(request).execute()
                                val jsonBody = response.body?.string() ?: ""
                                Log.i("MainActivity", JSONObject(jsonBody).toString())
                            } catch (e: Exception) { }
                        }
                    }
                }
            }
        )
    }

    fun persistState(authState: String) {
        viewModelScope.launch {
            userDataRepository.updateAuthState(authState)
        }
    }

    fun restoreState() {
        val jsonString = authState.value

        if( jsonString != null && !TextUtils.isEmpty(jsonString) ) {
            try {
                _authState = AuthState.jsonDeserialize(jsonString)
            } catch(jsonException: JSONException) {
                Log.d("MainActivity", "Failed to load auth state")
            }
        }
    }
}
