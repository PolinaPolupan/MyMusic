package com.example.mymusic.core.network.fake

import android.content.Context
import com.example.mymusic.core.common.IoDispatcher
import com.example.mymusic.core.network.MyMusicNetworkDataSource
import com.example.mymusic.core.network.R
import com.example.mymusic.core.network.model.PlaylistTrack
import com.example.mymusic.core.network.model.RecentlyPlayedTracksResponse
import com.example.mymusic.core.network.model.AlbumTracksResponse
import com.example.mymusic.core.network.model.PlaylistsTracksResponse
import com.example.mymusic.core.network.model.RecommendationsResponse
import com.example.mymusic.core.network.model.SavedAlbumsResponse
import com.example.mymusic.core.network.model.SavedPlaylistResponse
import com.example.mymusic.core.network.model.SpotifySimplifiedTrack
import com.example.mymusic.core.network.model.SpotifyTrack
import com.example.mymusic.core.network.model.UsersTopItemsResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * [FakeNetworkDataSource] implementation that provides static news resources to aid development
 */
class FakeNetworkDataSource @Inject constructor(
    @IoDispatcher val dispatcher: CoroutineDispatcher,
    @ApplicationContext val context: Context
) : MyMusicNetworkDataSource {
    override suspend fun getTopItems(type: String): List<SpotifyTrack> =
        withContext(dispatcher) {
            val inputStream = context.resources.openRawResource(R.raw.top_items)
                .bufferedReader().use { it.readText() }

            val response = Json.decodeFromString<UsersTopItemsResponse>(inputStream)
            response.items
        }

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

            Json.decodeFromString<RecentlyPlayedTracksResponse?>(inputStream)
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

    override suspend fun getSavedPlaylists(offset: Int, limit: Int): SavedPlaylistResponse? =
        withContext(dispatcher) {
            val inputStream = context.resources.openRawResource(R.raw.saved_playlists)
                .bufferedReader().use { it.readText() }

            Json.decodeFromString<SavedPlaylistResponse?>(inputStream)
        }

    override suspend fun getPlaylistTracks(id: String, fields: String): List<PlaylistTrack> =
        withContext(dispatcher) {
            val inputStream = context.resources.openRawResource(R.raw.playlist_tracks)
                .bufferedReader().use { it.readText() }

            val response = Json.decodeFromString<PlaylistsTracksResponse>(inputStream)
            response.items
        }

    override suspend fun getTrack(id: String): SpotifyTrack? =
        withContext(dispatcher) {
            val inputStream = context.resources.openRawResource(R.raw.track)
                .bufferedReader().use { it.readText() }

            Json.decodeFromString<SpotifyTrack?>(inputStream)
        }
}