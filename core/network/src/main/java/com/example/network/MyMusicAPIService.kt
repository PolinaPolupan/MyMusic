package com.example.network

import com.example.network.model.AlbumTracksResponse
import com.example.network.model.ErrorResponse
import com.example.network.model.PlaylistsTracksResponse
import com.example.network.model.RecommendationsResponse
import com.example.network.model.RecentlyPlayedTracksResponse
import com.example.network.model.SavedAlbumsResponse
import com.example.network.model.SavedPlaylistResponse
import com.example.network.model.SpotifyTrack
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyMusicAPIService {

    @GET("https://api.spotify.com/v1/recommendations?limit=10&seed_genres=pop")
    suspend fun getRecommendations(): NetworkResponse<RecommendationsResponse, ErrorResponse>

    @GET("https://api.spotify.com/v1/me/player/recently-played")
    suspend fun getRecentlyPlayed(
        @Query("before") before: String
    ): NetworkResponse<RecentlyPlayedTracksResponse, ErrorResponse>

    @GET("https://api.spotify.com/v1/albums/{id}/tracks")
    suspend fun getAlbumTracks(@Path("id") id: String): NetworkResponse<AlbumTracksResponse, ErrorResponse>

    @GET("https://api.spotify.com/v1/me/albums")
    suspend fun getSavedAlbums(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): NetworkResponse<SavedAlbumsResponse, ErrorResponse>

    @GET("https://api.spotify.com/v1/me/playlists")
    suspend fun getSavedPlaylists(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): NetworkResponse<SavedPlaylistResponse, ErrorResponse>

    @GET("https://api.spotify.com/v1/playlists/{id}/tracks")
    suspend fun getPlaylistTracks(
        @Path("id") id: String,
        @Query("fields") fields: String = "href, limit, next, offset, previous, total, items(added_at, added_by, is_local, track)"
    ): NetworkResponse<PlaylistsTracksResponse, ErrorResponse>

    @GET("https://api.spotify.com/v1/tracks/{id}")
    suspend fun getTrack(@Path("id") id: String): NetworkResponse<SpotifyTrack, ErrorResponse>
}
