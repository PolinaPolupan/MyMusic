package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.common.DefaultDispatcher
import com.example.data.mediator.AlbumsRemoteMediator
import com.example.data.mediator.PlaylistsRemoteMediator
import com.example.data.mediator.RecentlyPlayedRemoteMediator
import com.example.database.MusicDao
import com.example.database.MusicDatabase
import com.example.database.model.crossRef.AlbumTrackCrossRef
import com.example.database.model.crossRef.PlaylistTrackCrossRef
import com.example.database.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.database.model.entities.toExternal
import com.example.database.model.toExternal
import com.example.database.model.toExternalSimplified
import com.example.model.SimplifiedAlbum
import com.example.model.SimplifiedPlaylist
import com.example.model.SimplifiedTrack
import com.example.model.Track
import com.example.network.MyMusicAPIService
import com.example.network.model.AlbumTracksResponse
import com.example.network.model.ErrorResponse
import com.example.network.model.PlaylistTrack
import com.example.network.model.PlaylistsTracksResponse
import com.example.network.model.RecommendationsResponse
import com.example.network.model.SpotifySimplifiedTrack
import com.example.network.model.SpotifyTrack
import com.example.network.model.toLocal
import com.example.network.model.toLocalRecommendations
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

    override fun observeRecommendations(): Flow<List<Track>> {
         return musicDao.observeRecommendations().map { tracks ->
             tracks.toExternal()
        }
    }

    override fun observeTrack(id: String): Flow<Track> {
        return musicDao.observeTrack(id).map { track ->
            track.toExternal()
        }
    }

    override fun observeAlbum(id: String): Flow<SimplifiedAlbum> {
        return musicDao.observeAlbum(id).map { album ->
            album.toExternalSimplified()
        }
    }

    override fun observeAlbumTracks(id: String): Flow<List<SimplifiedTrack>> {
        return musicDao.observeAlbumTracks(id).map { tracks ->
            tracks.toExternal()
        }
    }

    override fun observePlaylist(id: String): Flow<SimplifiedPlaylist> {
        return musicDao.observePlaylist(id).map { playlist ->
            playlist.toExternal()
        }
    }

    override fun observePlaylistTracks(id: String): Flow<List<Track>> {
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
    override fun observeRecentlyPlayed(): Flow<PagingData<Track>> {

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
    override fun observeSavedAlbums(): Flow<PagingData<SimplifiedAlbum>> {

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
    override fun observeSavedPlaylists(): Flow<PagingData<SimplifiedPlaylist>> {
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