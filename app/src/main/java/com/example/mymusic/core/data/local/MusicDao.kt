package com.example.mymusic.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.mymusic.core.data.local.model.AlbumArtistCrossRef
import com.example.mymusic.core.data.local.model.LocalAlbum
import com.example.mymusic.core.data.local.model.LocalAlbumWithArtists
import com.example.mymusic.core.data.local.model.LocalArtist
import com.example.mymusic.core.data.local.model.LocalSimplifiedArtist
import com.example.mymusic.core.data.local.model.LocalTrack
import com.example.mymusic.core.data.local.model.LocalTrackWithArtists
import com.example.mymusic.core.data.local.model.TrackArtistCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {

    @Transaction
    @Query("SELECT * from tracks")
    fun observeAllTracks(): Flow<List<LocalTrackWithArtists>>

    @Query("SELECT * from artists")
    fun observeAllArtists(): Flow<List<LocalArtist>>

    @Query("SELECT * from simplifiedArtists")
    fun observeAllSimplifiedArtists(): Flow<List<LocalSimplifiedArtist>>

    @Transaction
    @Query("SELECT * from albums")
    fun observeAllAlbums(): Flow<List<LocalAlbumWithArtists>>

    @Transaction
    @Query("SELECT * FROM tracks WHERE trackId = :id")
    suspend fun getTrack(id: String): LocalAlbumWithArtists

    @Transaction
    @Query("SELECT * FROM albums WHERE albumId = :id")
    suspend fun getAlbum(id: String): LocalAlbumWithArtists

    @Upsert
    suspend fun upsertTracks(tracks: List<LocalTrack>)

    @Upsert
    suspend fun upsertArtist(artist: List<LocalArtist>)

    @Upsert
    suspend fun upsertSimplifiedArtists(simplifiedArtist: List<LocalSimplifiedArtist>)

    @Upsert
    suspend fun upsertAlbums(albums: List<LocalAlbum>)

    @Upsert
    suspend fun upsertTrackArtistCrossRef(ref: TrackArtistCrossRef)

    @Upsert
    suspend fun upsertAlbumArtistCrossRef(ref: AlbumArtistCrossRef)
}