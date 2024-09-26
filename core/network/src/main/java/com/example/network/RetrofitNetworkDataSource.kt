package com.example.network

import com.example.network.model.AlbumTracksResponse
import com.example.network.model.ErrorResponse
import com.example.network.model.PlaylistTrack
import com.example.network.model.PlaylistsTracksResponse
import com.example.network.model.RecentlyPlayedTracksResponse
import com.example.network.model.RecommendationsResponse
import com.example.network.model.SavedAlbum
import com.example.network.model.SavedAlbumsResponse
import com.example.network.model.SavedPlaylistResponse
import com.example.network.model.SpotifyPlayHistoryObject
import com.example.network.model.SpotifySimplifiedPlaylist
import com.example.network.model.SpotifySimplifiedTrack
import com.example.network.model.SpotifyTrack
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class RetrofitNetworkDataSource @Inject constructor(
    private val apiService: MyMusicAPIService
): MyMusicNetworkDataSource {

    override suspend fun getRecommendations(): List<SpotifyTrack> {
        val response = apiService.getRecommendations()
        val data = (response as? NetworkResponse.Success<RecommendationsResponse, ErrorResponse>?)?.body?.tracks ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    override suspend fun getRecentlyPlayed(before: String): List<SpotifyPlayHistoryObject> {
        val response = apiService.getRecentlyPlayed(before=before)
        val data = (response as? NetworkResponse.Success<RecentlyPlayedTracksResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    override suspend fun getAlbumTracks(id: String): List<SpotifySimplifiedTrack> {
        val response = apiService.getAlbumTracks(id)
        val data = (response as? NetworkResponse.Success<AlbumTracksResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    override suspend fun getSavedAlbums(offset: Int, limit: Int): List<SavedAlbum> {
        val response = apiService.getSavedAlbums(offset=offset, limit=limit)
        val data = (response as? NetworkResponse.Success<SavedAlbumsResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    override suspend fun getSavedPlaylists(offset: Int, limit: Int): List<SpotifySimplifiedPlaylist> {
        val response = apiService.getSavedPlaylists(offset=offset, limit=limit)
        val data = (response as? NetworkResponse.Success<SavedPlaylistResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    override suspend fun getPlaylistTracks(id: String, fields: String): List<PlaylistTrack> {
        val response = apiService.getPlaylistTracks(id)
        val data = (response as? NetworkResponse.Success<PlaylistsTracksResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    override suspend fun getTrack(id: String): SpotifyTrack? {
        val response = apiService.getTrack(id)
        val data = (response as? NetworkResponse.Success<SpotifyTrack, ErrorResponse>?)?.body

        return processResponse(response, data, null)
    }
}