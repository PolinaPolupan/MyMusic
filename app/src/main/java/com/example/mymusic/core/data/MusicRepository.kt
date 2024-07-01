package com.example.mymusic.core.data

import android.util.Log
import com.example.mymusic.core.data.di.DefaultDispatcher
import com.example.mymusic.core.data.local.MusicDao
import com.example.mymusic.core.data.local.model.AlbumArtistCrossRef
import com.example.mymusic.core.data.local.model.TrackArtistCrossRef
import com.example.mymusic.core.data.local.model.toExternal
import com.example.mymusic.core.data.network.MyMusicAPIService
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

    fun getRecommendationsStream(): Flow<List<Track>> {
         return musicDao.observeRecommendations().map { tracks ->
             tracks.toExternal()
        }
    }

    fun getRecentlyPlayedStream(): Flow<List<Track>> {
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

                for (track in recentlyPlayed) {
                    musicDao.upsertLocalPlayHistory(track.toLocal())
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

    suspend fun getRecentlyPlayed(): List<SpotifyPlayHistoryObject> {
        return when (val response = apiService.getRecentlyPlayed()) {
            is  NetworkResponse.Success -> {
                Log.d("MainActivity", response.body.toString())
                response.body.items
            }

            is NetworkResponse.NetworkError -> {
                Log.e("MainActivity", response.error.message ?: "Network Error")
                emptyList()
            }

            is NetworkResponse.ServerError -> {
                Log.e("MainActivity", ("Code: " + response.code.toString()))
                Log.e("MainActivity", response.error?.message ?: "Server Error")
                emptyList()
            }

            is NetworkResponse.UnknownError -> {
                Log.e("MainActivity", ("Code: " + response.code.toString()))
                Log.e("MainActivity", response.error.message ?: "Unknown Error")
                emptyList()
            }
        }
    }

    suspend fun getRecommendations(): List<SpotifyTrack> {
        return when (val response = apiService.getRecommendations()) {
            is  NetworkResponse.Success -> {
                Log.d("MainActivity", response.body.toString())
                response.body.tracks
            }

            is NetworkResponse.NetworkError -> {
                Log.e("MainActivity", response.error.message ?: "Network Error")
                emptyList()
            }

            is NetworkResponse.ServerError -> {
                Log.e("MainActivity", ("Code: " + response.code.toString()))
                Log.e("MainActivity", response.error?.message ?: "Server Error")
                emptyList()
            }

            is NetworkResponse.UnknownError -> {
                Log.e("MainActivity", ("Code: " + response.code.toString()))
                Log.e("MainActivity", response.error.message ?: "Unknown Error")
                emptyList()
            }
        }
    }
}