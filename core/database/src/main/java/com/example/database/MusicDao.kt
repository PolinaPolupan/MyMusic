package com.example.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomWarnings
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.database.model.LocalAlbumWithArtists
import com.example.database.model.LocalRecentlyPlayedWithArtists
import com.example.database.model.LocalSimplifiedTrackWithArtists
import com.example.database.model.LocalTrackWithArtists
import com.example.database.model.crossRef.AlbumArtistCrossRef
import com.example.database.model.crossRef.AlbumTrackCrossRef
import com.example.database.model.crossRef.PlaylistTrackCrossRef
import com.example.database.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.database.model.crossRef.TrackArtistCrossRef
import com.example.database.model.entities.LocalAlbum
import com.example.database.model.entities.LocalArtist
import com.example.database.model.entities.LocalPlaylist
import com.example.database.model.entities.LocalRecentlyPlayed
import com.example.database.model.entities.LocalRecommendation
import com.example.database.model.entities.LocalSavedAlbum
import com.example.database.model.entities.LocalSavedPlaylist
import com.example.database.model.entities.LocalSimplifiedArtist
import com.example.database.model.entities.LocalSimplifiedTrack
import com.example.database.model.entities.LocalTrack
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {

    @Transaction
    @Query("SELECT * from tracks")
    fun observeAllTracks(): Flow<List<LocalTrackWithArtists>>

    @Query("SELECT * from artists")
    fun observeAllArtists(): Flow<List<LocalArtist>>

    @Query("SELECT * from simplified_artists")
    fun observeAllSimplifiedArtists(): Flow<List<LocalSimplifiedArtist>>

    @Transaction
    @Query("SELECT * from albums")
    fun observeAllAlbums(): Flow<List<LocalAlbumWithArtists>>

    @Transaction
    @Query("SELECT * from tracks WHERE trackId IN (SELECT recommendationId FROM recommendations)")
    fun observeRecommendations(): Flow<List<LocalTrackWithArtists>>

    @Transaction
    @Query("SELECT * from recently_played")
    fun observeRecentlyPlayed(): PagingSource<Int, LocalRecentlyPlayedWithArtists>

    @Transaction
    @Query("SELECT * FROM albums WHERE albumId IN (SELECT savedALbumId FROM saved_albums)")
    fun observeSavedAlbums(): PagingSource<Int, LocalAlbumWithArtists>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH) // Is used to supress compiler warnings,
    // because we can access the other fields (e.g. trackId, trackName) via album and list of artists
    @Transaction
    @Query("SELECT * FROM tracks WHERE trackId = :id")
    fun observeTrack(id: String): Flow<LocalTrackWithArtists>

    @Transaction
    @Query("SELECT * FROM albums WHERE albumId = :id")
    fun observeAlbum(id: String): Flow<LocalAlbumWithArtists>

    @Query("SELECT * FROM playlists WHERE playlistId = :id")
    fun observePlaylist(id: String): Flow<LocalPlaylist>

    @Query("SELECT * FROM simplified_tracks WHERE simplifiedTrackId " +
            "IN (SELECT simplifiedTrackId FROM album_track WHERE albumId == :id)")
    fun observeAlbumTracks(id: String): Flow<List<LocalSimplifiedTrackWithArtists>>

    @Query("SELECT * FROM tracks WHERE trackId " +
            "IN (SELECT trackId FROM playlist_track WHERE playlistId == :id)")
    fun observePlaylistTracks(id: String): Flow<List<LocalTrackWithArtists>>

    @Query("SELECT * FROM playlists WHERE playlistId IN (SELECT savedPlaylistId FROM saved_playlists)")
    fun observeSavedPlaylists(): PagingSource<Int, LocalPlaylist>

    @Upsert
    suspend fun upsertTrack(track: LocalTrack)

    @Upsert
    suspend fun upsertTracks(tracks: List<LocalTrack>)

    @Upsert
    suspend fun upsertSimplifiedTrack(track: LocalSimplifiedTrack)

    @Upsert
    suspend fun upsertSimplifiedTracks(tracks: List<LocalSimplifiedTrack>)

    @Upsert
    suspend fun upsertArtists(artists: List<LocalArtist>)

    @Upsert
    suspend fun upsertSimplifiedArtists(simplifiedArtist: List<LocalSimplifiedArtist>)

    @Upsert
    suspend fun upsertAlbum(album: LocalAlbum)

    @Upsert
    suspend fun upsertAlbums(album: List<LocalAlbum>)

    @Upsert
    suspend fun upsertTrackArtistCrossRef(ref: TrackArtistCrossRef)

    @Upsert
    suspend fun upsertAlbumArtistCrossRef(ref: AlbumArtistCrossRef)

    @Upsert
    suspend fun upsertAlbumTrackCrossRef(ref: AlbumTrackCrossRef)

    @Upsert
    suspend fun upsertSimplifiedTrackArtistCrossRef(ref: SimplifiedTrackArtistCrossRef)

    @Upsert
    suspend fun upsertPlaylistTrackCrossRef(ref: PlaylistTrackCrossRef)

    @Upsert
    suspend fun upsertRecommendations(recommendations: List<LocalRecommendation>)

    @Upsert
    suspend fun upsertLocalPlayHistory(history: List<LocalRecentlyPlayed>)

    @Upsert
    suspend fun upsertSavedAlbums(albums: List<LocalSavedAlbum>)

    @Upsert
    suspend fun upsertSavedPlaylists(playlist: List<LocalSavedPlaylist>)

    @Upsert
    suspend fun upsertPlaylists(playlist: List<LocalPlaylist>)

    @Query("DELETE FROM recommendations")
    suspend fun deleteRecommendations()

    @Query("DELETE FROM recently_played")
    suspend fun deleteRecentlyPlayed()

    @Query("DELETE FROM simplified_tracks")
    suspend fun deleteSimplifiedTracks()

    @Query("DELETE FROM saved_albums")
    suspend fun deleteSavedAlbums()

    @Query("DELETE FROM saved_playlists")
    suspend fun deleteSavedPlaylists()
}