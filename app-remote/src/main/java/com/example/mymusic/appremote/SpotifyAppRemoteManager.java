package com.example.mymusic.appremote;


import static com.example.mymusic.core.common.Constants.CLIENT_ID;
import static com.example.mymusic.core.common.Constants.URL_AUTH_REDIRECT;

import android.content.Context;
import android.util.Log;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class SpotifyAppRemoteManager {

    @Inject
    @ApplicationContext
    Context context;

    @Inject
    public SpotifyAppRemoteManager () {}

    private SpotifyAppRemote mSpotifyAppRemote;

    public void onStart() {
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(URL_AUTH_REDIRECT)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(context, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    public void play(String uri) {
        // Play a playlist
        mSpotifyAppRemote.getPlayerApi().play(uri);

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                    }
                });
    }

    public void onStop() {
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    public void pause() {
        mSpotifyAppRemote.getPlayerApi().pause();
    }
}
