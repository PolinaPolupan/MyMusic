package com.example.network

import com.example.network.model.PlaylistTrack
import com.example.network.model.SavedAlbum
import com.example.network.model.SpotifyPlayHistoryObject
import com.example.network.model.SpotifySimplifiedPlaylist
import com.example.network.model.SpotifySimplifiedTrack
import com.example.network.model.SpotifyTrack

interface MyMusicNetworkDataSource {

    suspend fun getRecommendations(): List<SpotifyTrack>

    suspend fun getRecentlyPlayed(before: String): List<SpotifyPlayHistoryObject>

    suspend fun getAlbumTracks(id: String): List<SpotifySimplifiedTrack>

    suspend fun getSavedAlbums(offset: Int, limit: Int): List<SavedAlbum>

    suspend fun getSavedPlaylists(offset: Int, limit: Int): List<SpotifySimplifiedPlaylist>

    suspend fun getPlaylistTracks(id: String, fields: String): List<PlaylistTrack>

    suspend fun getTrack(id: String): SpotifyTrack?
}