package com.example.mymusic.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomWarnings
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.mymusic.core.data.local.model.AlbumArtistCrossRef
import com.example.mymusic.core.data.local.model.LocalAlbum
import com.example.mymusic.core.data.local.model.LocalAlbumWithArtists
import com.example.mymusic.core.data.local.model.LocalArtist
import com.example.mymusic.core.data.local.model.LocalRecentlyPlayed
import com.example.mymusic.core.data.local.model.LocalRecentlyPlayedWithArtists
import com.example.mymusic.core.data.local.model.LocalSimplifiedArtist
import com.example.mymusic.core.data.local.model.LocalTrack
import com.example.mymusic.core.data.local.model.LocalTrackWithArtists
import com.example.mymusic.core.data.local.model.LocalRecommendation
import com.example.mymusic.core.data.local.model.TrackArtistCrossRef
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
    fun observeRecentlyPlayed(): Flow<List<LocalRecentlyPlayedWithArtists>>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH) // Is used to supress compiler warnings,
    // because we can access the other fields (e.g. trackId, trackName) via album and list of artists
    @Transaction
    @Query("SELECT * FROM tracks WHERE trackId = :id")
    suspend fun getTrack(id: String): LocalTrackWithArtists

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH) // Is used to supress compiler warnings,
    // because we can access the other fields (e.g. trackId, trackName) via album and list of artists
    @Transaction
    @Query("SELECT * FROM tracks WHERE trackId = :id")
    fun observeTrack(id: String): Flow<LocalTrackWithArtists>

    @Transaction
    @Query("SELECT * FROM albums WHERE albumId = :id")
    suspend fun getAlbum(id: String): LocalAlbumWithArtists

    @Upsert
    suspend fun upsertTracks(tracks: List<LocalTrack>)

    @Upsert
    suspend fun upsertArtists(artists: List<LocalArtist>)

    @Upsert
    suspend fun upsertSimplifiedArtists(simplifiedArtist: List<LocalSimplifiedArtist>)

    @Upsert
    suspend fun upsertAlbums(albums: List<LocalAlbum>)

    @Upsert
    suspend fun upsertAlbum(album: LocalAlbum)

    @Upsert
    suspend fun upsertTrackArtistCrossRef(ref: TrackArtistCrossRef)

    @Upsert
    suspend fun upsertAlbumArtistCrossRef(ref: AlbumArtistCrossRef)

    @Upsert
    suspend fun upsertRecommendations(recommendations: List<LocalRecommendation>)

    @Upsert
    suspend fun upsertLocalPlayHistory(history: List<LocalRecentlyPlayed>)

    @Query("DELETE FROM tracks")
    suspend fun deleteAll()

    @Query("DELETE FROM recommendations")
    suspend fun deleteRecommendations()

    @Query("DELETE FROM recently_played")
    suspend fun deleteRecentlyPlayed()
}