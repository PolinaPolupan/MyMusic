package com.example.mymusic.core.network

import com.example.mymusic.core.network.model.PlaylistTrack
import com.example.mymusic.core.network.model.RecentlyPlayedTracksResponse
import com.example.mymusic.core.network.model.SavedAlbumsResponse
import com.example.mymusic.core.network.model.SavedPlaylistResponse
import com.example.mymusic.core.network.model.SpotifySimplifiedTrack
import com.example.mymusic.core.network.model.SpotifyTrack

interface MyMusicNetworkDataSource {
    suspend fun getTopItems(type: String): List<SpotifyTrack>

    suspend fun getRecommendations(): List<SpotifyTrack>

    suspend fun getRecentlyPlayed(before: String): RecentlyPlayedTracksResponse?

    suspend fun getAlbumTracks(id: String): List<SpotifySimplifiedTrack>

    suspend fun getSavedAlbums(offset: Int, limit: Int): SavedAlbumsResponse?

    suspend fun getSavedPlaylists(offset: Int, limit: Int): SavedPlaylistResponse?

    suspend fun getPlaylistTracks(id: String, fields: String): List<PlaylistTrack>

    suspend fun getTrack(id: String): SpotifyTrack?
}