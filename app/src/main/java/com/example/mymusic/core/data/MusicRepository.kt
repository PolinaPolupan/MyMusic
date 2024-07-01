package com.example.mymusic.core.data

import android.util.Log
import com.example.mymusic.core.data.di.DefaultDispatcher
import com.example.mymusic.core.data.local.MusicDao
import com.example.mymusic.core.data.local.model.AlbumArtistCrossRef
import com.example.mymusic.core.data.local.model.TrackArtistCrossRef
import com.example.mymusic.core.data.local.model.toExternal
import com.example.mymusic.core.data.network.MyMusicAPIService
import com.example.mymusic.core.data.network.model.ErrorResponse
import com.example.mymusic.core.data.network.model.RecentlyPlayedTracksResponse
import com.example.mymusic.core.data.network.model.RecommendationsResponse
import com.example.mymusic.core.data.network.model.SpotifyPlayHistoryObject
import com.example.mymusic.core.data.network.model.SpotifyTrack
import com.example.mymusic.core.data.network.model.toLocal
import com.example.mymusic.core.data.network.model.toLocalAlbum
import com.example.mymusic.core.data.network.model.toLocalRecommendations
import com.example.mymusic.core.data.network.model.toLocalSimplified
import com.example.mymusic.core.data.network.model.toLocalTracks
import com.example.mymusic.model.Track
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val musicDao: MusicDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val apiService: MyMusicAPIService
) {

    fun observeRecommendations(): Flow<List<Track>> {
         return musicDao.observeRecommendations().map { tracks ->
             tracks.toExternal()
        }
    }

    fun observeRecentlyPlayed(): Flow<List<Track>> {
        return musicDao.observeRecentlyPlayed().map {tracks ->
            tracks.toExternal()
        }
    }

    suspend fun refresh() {
        withContext(dispatcher) {
            val remoteMusic = getRecommendations()
            if (remoteMusic.isNotEmpty()) {

                musicDao.deleteRecommendations()
                musicDao.upsertTracks(remoteMusic.toLocal())
                musicDao.upsertRecommendations(remoteMusic.toLocalRecommendations())

                for (track in remoteMusic) {
                    for (artist in track.artists)
                        musicDao.upsertTrackArtistCrossRef(TrackArtistCrossRef(artist.id, track.id))
                    val album = track.album
                    musicDao.upsertAlbum(album.toLocalAlbum())
                    musicDao.upsertArtists(track.artists.toLocal())
                    musicDao.upsertSimplifiedArtists(track.artists.toLocalSimplified())
                    musicDao.upsertSimplifiedArtists(album.artists.toLocal())
                    for (artist in album.artists)
                        musicDao.upsertAlbumArtistCrossRef(AlbumArtistCrossRef(artist.id, album.id))
                }
            }

            val recentlyPlayed = getRecentlyPlayed()

            if (recentlyPlayed.isNotEmpty()) {

                musicDao.deleteRecentlyPlayed()
                musicDao.upsertTracks(recentlyPlayed.toLocalTracks())
                musicDao.upsertLocalPlayHistory(recentlyPlayed.toLocal())

                for (track in recentlyPlayed) {
                    for (artist in track.track.artists)
                        musicDao.upsertTrackArtistCrossRef(TrackArtistCrossRef(artist.id, track.track.id))
                    val album = track.track.album
                    musicDao.upsertAlbum(album.toLocalAlbum())
                    musicDao.upsertArtists(track.track.artists.toLocal())
                    musicDao.upsertSimplifiedArtists(track.track.artists.toLocalSimplified())
                    musicDao.upsertSimplifiedArtists(album.artists.toLocal())
                    for (artist in album.artists)
                        musicDao.upsertAlbumArtistCrossRef(AlbumArtistCrossRef(artist.id, album.id))
                }
            }
        }
    }

    private suspend fun getRecentlyPlayed(): List<SpotifyPlayHistoryObject> {
        val response = apiService.getRecentlyPlayed()
        val data = (response as? NetworkResponse.Success<RecentlyPlayedTracksResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    private suspend fun getRecommendations(): List<SpotifyTrack> {
        val response = apiService.getRecommendations()
        val data = (response as? NetworkResponse.Success<RecommendationsResponse, ErrorResponse>?)?.body?.tracks ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    private fun <S, E, T> processResponse(response: NetworkResponse<S, E>, successData: T, errorData: T): T {
        return when (response) {
            is  NetworkResponse.Success -> {
                Log.d("MainActivity", response.body.toString())
                successData
            }

            is NetworkResponse.NetworkError -> {
                Log.e("MainActivity", response.error.message ?: "Network Error")
                errorData
            }

            is NetworkResponse.ServerError -> {
                Log.e("MainActivity", ("Code: " + response.code.toString()))
                Log.e("MainActivity", response.error?.message ?: "Server Error")
                errorData
            }

            is NetworkResponse.UnknownError -> {
                Log.e("MainActivity", ("Code: " + response.code.toString()))
                Log.e("MainActivity", response.error.message ?: "Unknown Error")
                errorData
            }
        }
    }
}