package com.example.mymusic

import android.content.Context
import android.util.Log
import com.example.mymusic.core.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SpotifyAppRemoteManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    var spotifyAppRemote: SpotifyAppRemote? = null

    fun onStart() {
        // Set the connection parameters
        val connectionParams = ConnectionParams.Builder(Constants.CLIENT_ID)
            .setRedirectUri(Constants.URL_AUTH_REDIRECT)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(context, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")
                // Now you can start interacting with App Remote
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                // If Spotify is not installed, show alert dialog
                if (!SpotifyAppRemote.isSpotifyInstalled(context)) {
                    /*TODO*/
                }
            }
        })
    }

    fun play(uri: String) {
        // Then we will write some more code here.
        // Play a playlist
        spotifyAppRemote?.playerApi?.setShuffle(false)

        spotifyAppRemote?.playerApi?.play(uri)
        // Subscribe to PlayerState
        spotifyAppRemote?.playerApi?.subscribeToPlayerState()?.setEventCallback {
            val track: Track = it.track
            Log.d("MainActivity", track.name + " by " + track.uri)
        }
    }

    fun pause() {
        spotifyAppRemote?.playerApi?.pause()
    }


    fun onStop() {
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }
}