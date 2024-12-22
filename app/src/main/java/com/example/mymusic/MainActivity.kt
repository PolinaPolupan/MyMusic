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
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mymusic.appremote.SpotifyAppRemoteManager
import com.example.mymusic.core.designsystem.theme.MyMusicTheme
import com.example.mymusic.sync.SyncManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var spotifyAppRemoteManager: SpotifyAppRemoteManager

    @Inject lateinit var syncManager: SyncManager

    val viewModel: MainActivityViewModel by viewModels()

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

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            MyMusicTheme(dynamicColor = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyMusicApp(
                        appState = appState,
                        currentTrack = uiState.currentTrack,
                        isPlaying = uiState.isPlaying,
                        onPlayClick = {
                            viewModel.toggleIsPlaying(it)
                            if (!uiState.isPlaying) uiState.currentTrack?.let { track ->
                                Log.d("MainActivity", "Track ${track.uri}")
                                spotifyAppRemoteManager.play(track.uri)
                            } else {
                                spotifyAppRemoteManager.pause()
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        spotifyAppRemoteManager.onStart()
    }

    override fun onStop() {
        super.onStop()

        spotifyAppRemoteManager.onStop()
    }
}
