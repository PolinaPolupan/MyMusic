package com.example.mymusic.core.network.fake

import android.content.Context
import com.example.mymusic.core.network.MyMusicNetworkDataSource
import com.example.mymusic.core.network.R
import com.example.network.model.PlaylistTrack
import com.example.network.model.RecentlyPlayedTracksResponse
import com.example.network.model.SavedAlbumsResponse
import com.example.network.model.SavedPlaylistResponse
import com.example.network.model.SpotifySimplifiedTrack
import com.example.network.model.SpotifyTrack
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FakeNetworkDataSource @Inject constructor(
    @com.example.mymusic.core.common.IoDispatcher val dispatcher: CoroutineDispatcher,
    @ApplicationContext val context: Context
) : MyMusicNetworkDataSource {

    override suspend fun getRecommendations(): List<SpotifyTrack> =
        withContext(dispatcher) {
            val inputStream = context.resources.openRawResource(R.raw.recommendations)
                .bufferedReader().use { it.readText() }

            Json.decodeFromString<List<SpotifyTrack>>(inputStream)
        }

    override suspend fun getRecentlyPlayed(before: String): RecentlyPlayedTracksResponse? {
        TODO("Not yet implemented")
    }

    override suspend fun getAlbumTracks(id: String): List<SpotifySimplifiedTrack> {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedAlbums(offset: Int, limit: Int): SavedAlbumsResponse? {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedPlaylists(offset: Int, limit: Int): SavedPlaylistResponse? {
        TODO("Not yet implemented")
    }

    override suspend fun getPlaylistTracks(id: String, fields: String): List<PlaylistTrack> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrack(id: String): SpotifyTrack? {
        TODO("Not yet implemented")
    }

}