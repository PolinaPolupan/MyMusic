package com.example.network

import com.example.network.model.PlaylistTrack
import com.example.network.model.RecentlyPlayedTracksResponse
import com.example.network.model.SavedAlbumsResponse
import com.example.network.model.SavedPlaylistResponse
import com.example.network.model.SpotifySimplifiedTrack
import com.example.network.model.SpotifyTrack

interface MyMusicNetworkDataSource {

    suspend fun getRecommendations(): List<SpotifyTrack>

    suspend fun getRecentlyPlayed(before: String): RecentlyPlayedTracksResponse?

    suspend fun getAlbumTracks(id: String): List<SpotifySimplifiedTrack>

    suspend fun getSavedAlbums(offset: Int, limit: Int): SavedAlbumsResponse?

    suspend fun getSavedPlaylists(offset: Int, limit: Int): SavedPlaylistResponse?

    suspend fun getPlaylistTracks(id: String, fields: String): List<PlaylistTrack>

    suspend fun getTrack(id: String): SpotifyTrack?
}