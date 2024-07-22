package com.example.mymusic.core.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mymusic.core.data.di.DefaultDispatcher
import com.example.mymusic.core.data.local.MusicDao
import com.example.mymusic.core.data.local.MusicDatabase
import com.example.mymusic.core.data.local.model.crossRef.AlbumArtistCrossRef
import com.example.mymusic.core.data.local.model.crossRef.AlbumTrackCrossRef
import com.example.mymusic.core.data.local.model.crossRef.PlaylistTrackCrossRef
import com.example.mymusic.core.data.local.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.mymusic.core.data.local.model.crossRef.TrackArtistCrossRef
import com.example.mymusic.core.data.local.model.entities.toExternal
import com.example.mymusic.core.data.local.model.toExternal
import com.example.mymusic.core.data.local.model.toExternalSimplified
import com.example.mymusic.core.data.network.MyMusicAPIService
import com.example.mymusic.core.data.network.model.AlbumTracksResponse
import com.example.mymusic.core.data.network.model.ErrorResponse
import com.example.mymusic.core.data.network.model.PlaylistTrack
import com.example.mymusic.core.data.network.model.PlaylistsTracksResponse
import com.example.mymusic.core.data.network.model.RecentlyPlayedTracksResponse
import com.example.mymusic.core.data.network.model.RecommendationsResponse
import com.example.mymusic.core.data.network.model.SavedAlbum
import com.example.mymusic.core.data.network.model.SavedAlbumsResponse
import com.example.mymusic.core.data.network.model.SavedPlaylistResponse
import com.example.mymusic.core.data.network.model.SpotifySimplifiedPlaylist
import com.example.mymusic.core.data.network.model.SpotifySimplifiedTrack
import com.example.mymusic.core.data.network.model.SpotifyTrack
import com.example.mymusic.core.data.network.model.toLocal
import com.example.mymusic.core.data.network.model.toLocalAlbum
import com.example.mymusic.core.data.network.model.toLocalRecommendations
import com.example.mymusic.core.data.network.model.toLocalSaved
import com.example.mymusic.core.data.network.model.toLocalSimplified
import com.example.mymusic.core.data.network.model.toLocalSimplifiedTrack
import com.example.mymusic.core.data.network.model.toLocalTrack
import com.example.mymusic.model.SimplifiedAlbum
import com.example.mymusic.model.SimplifiedPlaylist
import com.example.mymusic.model.SimplifiedTrack
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
    private val apiService: MyMusicAPIService,
    private val database: MusicDatabase
) {

    fun observeRecommendations(): Flow<List<Track>> {
         return musicDao.observeRecommendations().map { tracks ->
             tracks.toExternal()
        }
    }

    fun observeTrack(id: String): Flow<Track> {
        return musicDao.observeTrack(id).map { track ->
            track.toExternal()
        }
    }

    fun observeAlbum(id: String): Flow<SimplifiedAlbum> {
        return musicDao.observeAlbum(id).map { album ->
            album.toExternalSimplified()
        }
    }

    fun observeAlbumTracks(id: String): Flow<List<SimplifiedTrack>> {
        return musicDao.observeAlbumTracks(id).map { tracks ->
            tracks.toExternal()
        }
    }

    fun observePlaylist(id: String): Flow<SimplifiedPlaylist> {
        return musicDao.observePlaylist(id).map { playlist ->
            playlist.toExternal()
        }
    }

    fun observePlaylistTracks(id: String): Flow<List<Track>> {
        return musicDao.observePlaylistTracks(id).map { tracks ->
            tracks.toExternal()
        }
    }

    fun observeSavedAlbums(): Flow<List<SimplifiedAlbum>> {
        return musicDao.observeSavedAlbums().map { album ->
            album.toExternal()
        }
    }

    fun observeSavedPlaylists(): Flow<List<SimplifiedPlaylist>> {
        return musicDao.observeSavedPlaylists().map { playlist ->
            playlist.toExternal()
        }
    }

    suspend fun loadTrack(id: String) {
        withContext(dispatcher) {
            val track = getTrack(id)

            if (track != null) {
                upsertTrack(track)
            }
        }
    }

    suspend fun loadAlbumTracks(id: String) {
        withContext(dispatcher) {
            val tracks = getAlbumTracks(id)

            if (tracks.isNotEmpty()) {
                musicDao.upsertSimplifiedTracks(tracks.toLocal())

                for (track in tracks) {
                    musicDao.upsertSimplifiedArtists(track.artists.toLocal())
                    musicDao.upsertAlbumTrackCrossRef(AlbumTrackCrossRef(track.id, id))
                    for (artist in track.artists)
                        musicDao.upsertSimplifiedTrackArtistCrossRef(SimplifiedTrackArtistCrossRef(track.id, artist.id))
                }
            }
        }
    }

    suspend fun loadPlaylistTracks(id: String) {
        withContext(dispatcher) {
            val tracks = getPlaylistTracks(id)

            if (tracks.isNotEmpty()) {

                for (track in tracks) {
                    musicDao.upsertPlaylistTrackCrossRef(PlaylistTrackCrossRef(id, track.track.id))
                    upsertTrack(track.track)
                }
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun observeRecentlyPlayed(): Flow<PagingData<Track>> {

        val pagingSourceFactory = { database.musicDao().getRecentlyPlayed() }

        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = RecentlyPlayedRemoteMediator(
                apiService,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map { it ->
                it.map { it.toExternal() }
            }
    }

    suspend fun refresh() {
        withContext(dispatcher) {

            musicDao.deleteSimplifiedTracks()

            val remoteMusic = getRecommendations()

            if (remoteMusic.isNotEmpty()) {

                musicDao.deleteRecommendations()

                for (track in remoteMusic) {
                    upsertTrack(track)
                }

                musicDao.upsertRecommendations(remoteMusic.toLocalRecommendations())
            }

            val time = System.currentTimeMillis()
            val recentlyPlayed = getRecentlyPlayed(time.toString())?.items

            if (recentlyPlayed != null) {
                if (recentlyPlayed.isNotEmpty()) {

                    musicDao.deleteRecentlyPlayed()

                    for (track in recentlyPlayed) {
                        upsertTrack(track.track)
                    }

                    musicDao.upsertLocalPlayHistory(recentlyPlayed.toLocal())
                }
            }

            val savedAlbums = getSavedAlbums()

            if (savedAlbums.isNotEmpty()) {

                musicDao.deleteSavedAlbums()
                musicDao.upsertSavedAlbums(savedAlbums.toLocal())
                musicDao.upsertAlbums(savedAlbums.toLocalAlbum())
                for (album in savedAlbums) {
                    val album = album.album
                    musicDao.upsertSimplifiedArtists(album.artists.toLocal())
                    for (artist in album.artists)
                        musicDao.upsertAlbumArtistCrossRef(AlbumArtistCrossRef(albumId = album.id, simplifiedArtistId = artist.id))
                }
            }

            val savedPlaylists = getSavedPlaylists()

            if (savedPlaylists.isNotEmpty()) {
                musicDao.deleteSavedPlaylists()
                musicDao.upsertPlaylists(savedPlaylists.toLocal())
                musicDao.upsertSavedPlaylists(savedPlaylists.toLocalSaved())
            }
        }
    }

    private suspend fun upsertTrack(track: SpotifyTrack) {
        musicDao.upsertTrack(track.toLocalTrack())
        musicDao.upsertSimplifiedTrack(track.toLocalSimplifiedTrack())
        for (artist in track.artists) {
            musicDao.upsertTrackArtistCrossRef(TrackArtistCrossRef(artist.id, track.id))
            musicDao.upsertSimplifiedTrackArtistCrossRef(SimplifiedTrackArtistCrossRef(artist.id, track.id))
        }

        val album = track.album
        musicDao.upsertAlbum(album.toLocal())
        musicDao.upsertArtists(track.artists.toLocal())
        musicDao.upsertSimplifiedArtists(track.artists.toLocalSimplified())
        musicDao.upsertSimplifiedArtists(album.artists.toLocal())
        for (artist in album.artists)
            musicDao.upsertAlbumArtistCrossRef(AlbumArtistCrossRef(artist.id, album.id))
    }

    private suspend fun getRecentlyPlayed(before: String): RecentlyPlayedTracksResponse? {
        val response = apiService.getRecentlyPlayed(before = before)
        val data = (response as? NetworkResponse.Success<RecentlyPlayedTracksResponse, ErrorResponse>?)?.body

        return processResponse(response, data, null, request = "Get recently played")
    }

    private suspend fun getRecommendations(): List<SpotifyTrack> {
        val response = apiService.getRecommendations()
        val data = (response as? NetworkResponse.Success<RecommendationsResponse, ErrorResponse>?)?.body?.tracks ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    private suspend fun getAlbumTracks(id: String): List<SpotifySimplifiedTrack> {
        val response = apiService.getAlbumTracks(id)
        val data = (response as? NetworkResponse.Success<AlbumTracksResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    private suspend fun getPlaylistTracks(id: String): List<PlaylistTrack> {
        val response = apiService.getPlaylistTracks(id)
        val data = (response as? NetworkResponse.Success<PlaylistsTracksResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    private suspend fun getSavedAlbums(): List<SavedAlbum> {
        val response = apiService.getSavedAlbums()
        val data = (response as? NetworkResponse.Success<SavedAlbumsResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    private suspend fun getSavedPlaylists(): List<SpotifySimplifiedPlaylist> {
        val response = apiService.getSavedPlaylists()
        val data = (response as? NetworkResponse.Success<SavedPlaylistResponse, ErrorResponse>?)?.body?.items ?: emptyList()

        return processResponse(response, data, emptyList())
    }

    private suspend fun getTrack(id: String): SpotifyTrack? {
        val response = apiService.getTrack(id)
        val data = (response as? NetworkResponse.Success<SpotifyTrack, ErrorResponse>?)?.body

        return processResponse(response, data, null)
    }
}

fun <S, E, T> processResponse(response: NetworkResponse<S, E>, successData: T, errorData: T, request: String? = null): T {
    return when (response) {
        is  NetworkResponse.Success -> {
            Log.d("MainActivity", "Request: $request is successful: ${response.body.toString()}")
            successData
        }

        is NetworkResponse.NetworkError -> {
            Log.e("MainActivity", "Request: $request failed ${response.error.message ?: "Network Error"}")
            errorData
        }

        is NetworkResponse.ServerError -> {
            Log.e("MainActivity", ("Code: " + response.code.toString()))
            Log.e("MainActivity", "Request: $request failed ${response.error?.message ?: "Server Error"}")
            errorData
        }

        is NetworkResponse.UnknownError -> {
            Log.e("MainActivity", ("Code: " + response.code.toString()))
            Log.e("MainActivity", "Request: $request failed ${response.error.message ?: "Unknown Error"}")
            errorData
        }
    }
}