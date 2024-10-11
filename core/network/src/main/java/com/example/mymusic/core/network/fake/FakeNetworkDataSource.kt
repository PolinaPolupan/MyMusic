package com.example.mymusic.core.network.fake

import android.content.Context
import com.example.mymusic.core.common.IoDispatcher
import com.example.mymusic.core.network.MyMusicNetworkDataSource
import com.example.mymusic.core.network.R
import com.example.network.model.PlaylistTrack
import com.example.mymusic.core.network.model.RecentlyPlayedTracksResponse
import com.example.network.model.AlbumTracksResponse
import com.example.network.model.RecommendationsResponse
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
    @IoDispatcher val dispatcher: CoroutineDispatcher,
    @ApplicationContext val context: Context
) : MyMusicNetworkDataSource {

    override suspend fun getRecommendations(): List<SpotifyTrack> =
        withContext(dispatcher) {
            val inputStream = context.resources.openRawResource(R.raw.recommendations)
                .bufferedReader().use { it.readText() }

            val response = Json.decodeFromString<RecommendationsResponse>(inputStream)
            response.tracks
        }

    override suspend fun getRecentlyPlayed(before: String): RecentlyPlayedTracksResponse? =
        withContext(dispatcher) {
            val inputStream = context.resources.openRawResource(R.raw.recently_played)
                .bufferedReader().use { it.readText() }

            val response = Json.decodeFromString<RecentlyPlayedTracksResponse?>(inputStream)
            response
        }

    override suspend fun getAlbumTracks(id: String): List<SpotifySimplifiedTrack> =
        withContext(dispatcher) {
            val inputStream = context.resources.openRawResource(R.raw.album_tracks)
                .bufferedReader().use { it.readText() }

            val response = Json.decodeFromString<AlbumTracksResponse>(inputStream)
            response.items
        }

    override suspend fun getSavedAlbums(offset: Int, limit: Int): SavedAlbumsResponse? =
        withContext(dispatcher) {
            val inputStream = context.resources.openRawResource(R.raw.saved_albums)
                .bufferedReader().use { it.readText() }

           Json.decodeFromString<SavedAlbumsResponse?>(inputStream)
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