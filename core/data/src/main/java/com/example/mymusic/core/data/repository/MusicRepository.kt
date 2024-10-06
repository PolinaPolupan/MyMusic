package com.example.mymusic.core.data.repository

import androidx.paging.PagingData
import com.example.mymusic.core.model.SimplifiedAlbum
import com.example.mymusic.core.model.SimplifiedPlaylist
import com.example.mymusic.core.model.SimplifiedTrack
import com.example.mymusic.core.model.Track
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    /**
     * Returns recommendations as a stream of tracks
     */
    fun observeRecommendations(): Flow<List<Track>>

    /**
     * Returns a stream of [Track] by id
     */
    fun observeTrack(id: String): Flow<Track>

    /**
     * Returns a stream of [SimplifiedAlbum] by id
     */
    fun observeAlbum(id: String): Flow<SimplifiedAlbum>

    /**
     * Returns a stream of simplified tracks for a specific album
     */
    fun observeAlbumTracks(id: String): Flow<List<SimplifiedTrack>>

    /**
     * Returns a stream of [SimplifiedPlaylist] by id
     */
    fun observePlaylist(id: String): Flow<SimplifiedPlaylist>

    /**
     * Returns a stream of simplified tracks for a specific playlist
     */
    fun observePlaylistTracks(id: String): Flow<List<Track>>

    /**
     * Loads a specific track into the database
     */
    suspend fun loadTrack(id: String)

    /**
     * Loads tracks of a specific album into the database
     */
    suspend fun loadAlbumTracks(id: String)

    /**
     * Loads tracks of a specific playlist into the database
     */
    suspend fun loadPlaylistTracks(id: String)

    /**
     * Returns a stream of recently played tracks as a paging data
     */
    fun observeRecentlyPlayed(): Flow<PagingData<Track>>

    /**
     * Returns a stream of saved albums as a paging data
     */
    fun observeSavedAlbums(): Flow<PagingData<SimplifiedAlbum>>

    /**
     * Returns a stream of saved playlists as a paging data
     */
    fun observeSavedPlaylists(): Flow<PagingData<SimplifiedPlaylist>>

    /**
     * Refreshes database
     */
    suspend fun refresh()
}