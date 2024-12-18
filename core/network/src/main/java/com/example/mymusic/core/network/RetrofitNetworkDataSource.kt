package com.example.mymusic.core.network

import com.example.mymusic.core.network.model.AlbumTracksResponse
import com.example.mymusic.core.network.model.ErrorResponse
import com.example.mymusic.core.network.model.PlaylistTrack
import com.example.mymusic.core.network.model.PlaylistsTracksResponse
import com.example.mymusic.core.network.model.RecentlyPlayedTracksResponse
import com.example.mymusic.core.network.model.RecommendationsResponse
import com.example.mymusic.core.network.model.SavedAlbumsResponse
import com.example.mymusic.core.network.model.SavedPlaylistResponse
import com.example.mymusic.core.network.model.SpotifySimplifiedTrack
import com.example.mymusic.core.network.model.SpotifyTrack
import com.example.mymusic.core.network.model.UsersTopItemsResponse
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class RetrofitNetworkDataSource @Inject constructor(
    private val apiService: MyMusicAPIService
): MyMusicNetworkDataSource {
    override suspend fun getTopItems(type: String): List<SpotifyTrack> {
        val response = apiService.getTopItems(type)
        val data = (response as? NetworkResponse.Success<UsersTopItemsResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    override suspend fun getRecommendations(): List<SpotifyTrack> {
        val response = apiService.getRecommendations()
        val data = (response as? NetworkResponse.Success<RecommendationsResponse, ErrorResponse>?)?.body?.tracks ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    override suspend fun getRecentlyPlayed(before: String): RecentlyPlayedTracksResponse? {
        val response = apiService.getRecentlyPlayed(before=before)
        val data = (response as? NetworkResponse.Success<RecentlyPlayedTracksResponse, ErrorResponse>?)?.body

        return processResponse(response, data, null)
    }

    override suspend fun getAlbumTracks(id: String): List<SpotifySimplifiedTrack> {
        val response = apiService.getAlbumTracks(id)
        val data = (response as? NetworkResponse.Success<AlbumTracksResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    override suspend fun getSavedAlbums(offset: Int, limit: Int): SavedAlbumsResponse? {
        val response = apiService.getSavedAlbums(offset=offset, limit=limit)
        val data = (response as? NetworkResponse.Success<SavedAlbumsResponse, ErrorResponse>?)?.body

        return processResponse(response, data, null)
    }

    override suspend fun getSavedPlaylists(offset: Int, limit: Int): SavedPlaylistResponse? {
        val response = apiService.getSavedPlaylists(offset=offset, limit=limit)
        val data = (response as? NetworkResponse.Success<SavedPlaylistResponse, ErrorResponse>?)?.body

        return processResponse(response, data, null)
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