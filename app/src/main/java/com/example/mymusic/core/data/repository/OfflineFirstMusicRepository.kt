package com.example.mymusic.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mymusic.core.data.di.DefaultDispatcher
import com.example.mymusic.core.data.local.MusicDao
import com.example.mymusic.core.data.local.MusicDatabase
import com.example.mymusic.core.data.local.model.crossRef.AlbumTrackCrossRef
import com.example.mymusic.core.data.local.model.crossRef.PlaylistTrackCrossRef
import com.example.mymusic.core.data.local.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.mymusic.core.data.local.model.entities.toExternal
import com.example.mymusic.core.data.local.model.toExternal
import com.example.mymusic.core.data.local.model.toExternalSimplified
import com.example.mymusic.core.data.mediator.AlbumsRemoteMediator
import com.example.mymusic.core.data.mediator.PlaylistsRemoteMediator
import com.example.mymusic.core.data.mediator.RecentlyPlayedRemoteMediator
import com.example.mymusic.core.data.network.MyMusicAPIService
import com.example.mymusic.core.data.network.model.AlbumTracksResponse
import com.example.mymusic.core.data.network.model.ErrorResponse
import com.example.mymusic.core.data.network.model.PlaylistTrack
import com.example.mymusic.core.data.network.model.PlaylistsTracksResponse
import com.example.mymusic.core.data.network.model.RecommendationsResponse
import com.example.mymusic.core.data.network.model.SpotifySimplifiedTrack
import com.example.mymusic.core.data.network.model.SpotifyTrack
import com.example.mymusic.core.data.network.model.toLocal
import com.example.mymusic.core.data.network.model.toLocalRecommendations
import com.example.model.SimplifiedAlbum
import com.example.model.SimplifiedPlaylist
import com.example.model.SimplifiedTrack
import com.example.model.Track
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineFirstMusicRepository @Inject constructor(
    private val musicDao: MusicDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val apiService: MyMusicAPIService,
    private val database: MusicDatabase
): MusicRepository {

    override fun observeRecommendations(): Flow<List<com.example.model.Track>> {
         return musicDao.observeRecommendations().map { tracks ->
             tracks.toExternal()
        }
    }

    override fun observeTrack(id: String): Flow<com.example.model.Track> {
        return musicDao.observeTrack(id).map { track ->
            track.toExternal()
        }
    }

    override fun observeAlbum(id: String): Flow<com.example.model.SimplifiedAlbum> {
        return musicDao.observeAlbum(id).map { album ->
            album.toExternalSimplified()
        }
    }

    override fun observeAlbumTracks(id: String): Flow<List<com.example.model.SimplifiedTrack>> {
        return musicDao.observeAlbumTracks(id).map { tracks ->
            tracks.toExternal()
        }
    }

    override fun observePlaylist(id: String): Flow<com.example.model.SimplifiedPlaylist> {
        return musicDao.observePlaylist(id).map { playlist ->
            playlist.toExternal()
        }
    }

    override fun observePlaylistTracks(id: String): Flow<List<com.example.model.Track>> {
        return musicDao.observePlaylistTracks(id).map { tracks ->
            tracks.toExternal()
        }
    }

    override suspend fun loadTrack(id: String) {
        withContext(dispatcher) {
            val track = getTrack(id)

            if (track != null) {
                upsertTrack(track, musicDao)
            }
        }
    }

    override suspend fun loadAlbumTracks(id: String) {
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

    override suspend fun loadPlaylistTracks(id: String) {
        withContext(dispatcher) {
            val tracks = getPlaylistTracks(id)

            if (tracks.isNotEmpty()) {

                for (track in tracks) {
                    musicDao.upsertPlaylistTrackCrossRef(PlaylistTrackCrossRef(id, track.track.id))
                    upsertTrack(track.track, musicDao)
                }
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun observeRecentlyPlayed(): Flow<PagingData<com.example.model.Track>> {

        val pagingSourceFactory = { database.musicDao().observeRecentlyPlayed() }

        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
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

    @OptIn(ExperimentalPagingApi::class)
    override fun observeSavedAlbums(): Flow<PagingData<com.example.model.SimplifiedAlbum>> {

        val pagingSourceFactory = { database.musicDao().observeSavedAlbums() }

        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            remoteMediator = AlbumsRemoteMediator(
                apiService,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map { it ->
                it.map { it.toExternalSimplified() }
            }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun observeSavedPlaylists(): Flow<PagingData<com.example.model.SimplifiedPlaylist>> {
        val pagingSourceFactory = { database.musicDao().observeSavedPlaylists() }

        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            remoteMediator = PlaylistsRemoteMediator(
                apiService,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map { it.map { it.toExternal() } }
    }

    override suspend fun refresh() {
        withContext(dispatcher) {

            val remoteMusic = getRecommendations()

            if (remoteMusic.isNotEmpty()) {

                musicDao.deleteRecommendations()

                for (track in remoteMusic) {
                    upsertTrack(track, musicDao)
                }

                musicDao.upsertRecommendations(remoteMusic.toLocalRecommendations())
            }
        }
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

    private suspend fun getTrack(id: String): SpotifyTrack? {
        val response = apiService.getTrack(id)
        val data = (response as? NetworkResponse.Success<SpotifyTrack, ErrorResponse>?)?.body

        return processResponse(response, data, null)
    }
}