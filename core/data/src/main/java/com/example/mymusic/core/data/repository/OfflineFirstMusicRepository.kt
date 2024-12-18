package com.example.mymusic.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mymusic.core.common.DefaultDispatcher
import com.example.mymusic.core.data.mediator.AlbumsRemoteMediator
import com.example.mymusic.core.data.mediator.PlaylistsRemoteMediator
import com.example.mymusic.core.data.mediator.RecentlyPlayedRemoteMediator
import com.example.mymusic.core.database.MusicDao
import com.example.mymusic.core.database.MusicDatabase
import com.example.mymusic.core.database.model.crossRef.AlbumTrackCrossRef
import com.example.mymusic.core.database.model.crossRef.PlaylistTrackCrossRef
import com.example.mymusic.core.database.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.mymusic.core.database.model.entities.toExternal
import com.example.mymusic.core.database.model.toExternal
import com.example.mymusic.core.database.model.toExternalSimplified
import com.example.mymusic.core.model.SimplifiedAlbum
import com.example.mymusic.core.model.SimplifiedPlaylist
import com.example.mymusic.core.model.SimplifiedTrack
import com.example.mymusic.core.model.Track
import com.example.mymusic.core.network.MyMusicNetworkDataSource
import com.example.mymusic.core.network.model.PlaylistTrack
import com.example.mymusic.core.network.model.SpotifySimplifiedTrack
import com.example.mymusic.core.network.model.SpotifyTrack
import com.example.mymusic.core.network.model.toLocal
import com.example.mymusic.core.network.model.toLocalRecommendations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineFirstMusicRepository @Inject constructor(
    private val musicDao: MusicDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val networkDataSource: MyMusicNetworkDataSource,
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
                networkDataSource,
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
                networkDataSource,
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
                networkDataSource,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map { it.map { it.toExternal() } }
    }

    override suspend fun refresh() {
        withContext(dispatcher) {

            // Get recommendations endpoint is deprecated
            //val remoteMusic = getRecommendations()

            val remoteMusic = networkDataSource.getTopItems("tracks")

            if (remoteMusic.isNotEmpty()) {

                musicDao.deleteRecommendations()

                for (track in remoteMusic) {
                    upsertTrack(track, musicDao)
                }

                musicDao.upsertRecommendations(remoteMusic.toLocalRecommendations())
            }
        }
    }

    private suspend fun getRecommendations(): List<SpotifyTrack> = networkDataSource.getRecommendations()

    private suspend fun getAlbumTracks(id: String): List<SpotifySimplifiedTrack> = networkDataSource.getAlbumTracks(id)

    private suspend fun getPlaylistTracks(id: String): List<PlaylistTrack> = networkDataSource.getPlaylistTracks(id, fields = "href, limit, next, offset, previous, total, items(added_at, added_by, is_local, track)")

    private suspend fun getTrack(id: String): SpotifyTrack? = networkDataSource.getTrack(id)
}