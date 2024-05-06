package com.example.mymusic

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.data.network.SpotifyAPIServiceConstants
import com.example.mymusic.feature.login.LoginScreen
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.android.appremote.api.SpotifyAppRemote.isSpotifyInstalled
import com.spotify.protocol.types.Track
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.spotify.sdk.android.auth.LoginActivity.REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    var spotifyAppRemote: SpotifyAppRemote? = null

    val viewModel: LoginActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            // This app is only ever in dark mode, so hard code detectDarkMode to true.
            SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT, detectDarkMode = { true })
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            MyMusicTheme(dynamicColor = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginClick = {
                            startLoginFlow()
                            val intent = getMainActivityIntent()
                            startActivity(intent)
                            finish()
                        },
                        loginViewModel = viewModel
                    )
                }
            }
        }
    }

    private fun startLoginFlow() {
        val builder = AuthorizationRequest.Builder(
            SpotifyAPIServiceConstants.CLIENT_ID, AuthorizationResponse.Type.TOKEN,
            SpotifyAPIServiceConstants.REDIRECT_URI
        )
        val scopes = arrayOf("streaming")
        builder.setScopes(scopes)
        val request = builder.build()
        AuthorizationClient.openLoginActivity(this,
            REQUEST_CODE, request)
        //AuthorizationClient.getResponse()
    }

    override fun onStart() {
        super.onStart()

        // Set the connection parameters
        val connectionParams = ConnectionParams.Builder(SpotifyAPIServiceConstants.CLIENT_ID)
            .setRedirectUri(SpotifyAPIServiceConstants.REDIRECT_URI)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")
                // Now you can start interacting with App Remote
                viewModel.setIsSpotifyInstalled(true)
                viewModel.setShowDialog(false)
                connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                // If Spotify is not installed, show alert dialog
                if (!isSpotifyInstalled(applicationContext)) {
                    viewModel.setIsSpotifyInstalled(false)
                    viewModel.setShowDialog(true)
                }
            }
        })
    }

    private fun connected() {
        // Then we will write some more code here.
        // Play a playlist
        spotifyAppRemote?.playerApi?.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
        // Subscribe to PlayerState
        spotifyAppRemote?.playerApi?.subscribeToPlayerState()?.setEventCallback {
            val track: Track = it.track
            Log.d("MainActivity", track.name + " by " + track.artist.name)
        }
    }

    override fun onStop() {
        super.onStop()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }

    private fun getMainActivityIntent(): Intent {
        return Intent(this, MainActivity::class.java)
    }
}