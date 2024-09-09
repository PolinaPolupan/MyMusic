package com.example.mymusic

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.common.Constants
import com.example.designsystem.theme.MyMusicTheme
import com.example.sync.SyncManager
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var spotifyAppRemote: SpotifyAppRemote? = null

    @Inject lateinit var syncManager: SyncManager

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load data from the network and refresh database
        syncManager.refresh()

        enableEdgeToEdge(
            // This app is only ever in dark mode, so hard code detectDarkMode to true.
            SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT, detectDarkMode = { true })
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        // Debug line
        //applicationContext.deleteDatabase("music.db")
        setContent {

            val appState = rememberMyMusicAppState()

            MyMusicTheme(dynamicColor = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyMusicApp(appState)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // Set the connection parameters
        val connectionParams = ConnectionParams.Builder(Constants.CLIENT_ID)
            .setRedirectUri(Constants.URL_AUTH_REDIRECT)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")
                // Now you can start interacting with App Remote
                connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                // If Spotify is not installed, show alert dialog
                if (!SpotifyAppRemote.isSpotifyInstalled(applicationContext)) {
                    /*TODO*/
                }
            }
        })
    }

    private fun connected() {
//        // Then we will write some more code here.
//        // Play a playlist
//        spotifyAppRemote?.playerApi?.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
//        // Subscribe to PlayerState
//        spotifyAppRemote?.playerApi?.subscribeToPlayerState()?.setEventCallback {
//            val track: SpotifyTrack = it.track
//            Log.d("MainActivity", track.name + " by " + track.artist.name)
//        }
    }

    override fun onStop() {
        super.onStop()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }
}
