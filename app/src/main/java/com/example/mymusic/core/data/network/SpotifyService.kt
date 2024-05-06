package com.example.mymusic.core.data.network

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.activity.ComponentActivity
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.spotify.sdk.android.auth.LoginActivity.REQUEST_CODE

object SpotifyService {
    private const val CLIENT_ID = "964f3c103fd64e478848b059f9240d73"
    private const val  REDIRECT_URI = "com.example.mymusic://callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    private var connectionParams: ConnectionParams = ConnectionParams.Builder(CLIENT_ID)
        .setRedirectUri(REDIRECT_URI)
        .showAuthView(true)
        .build()

    fun connect(context: Context) {

        if (spotifyAppRemote?.isConnected == true) {
            return
        }
        val connectionListener = object : Connector.ConnectionListener {
            override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                SpotifyService.spotifyAppRemote = spotifyAppRemote
            }
            override fun onFailure(throwable: Throwable) {
                Log.e("SpotifyService", throwable.message, throwable)
            }
        }
        SpotifyAppRemote.connect(context, connectionParams, connectionListener)
    }
}
fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}