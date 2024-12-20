package com.example.mymusic.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymusic.core.database.model.entities.LocalAlbum
import com.example.mymusic.core.database.model.entities.LocalArtist
import com.example.mymusic.core.database.model.entities.LocalPlaylist
import com.example.mymusic.core.database.model.entities.LocalRecentlyPlayed
import com.example.mymusic.core.database.model.entities.LocalRecommendation
import com.example.mymusic.core.database.model.entities.LocalSavedAlbum
import com.example.mymusic.core.database.model.entities.LocalSavedPlaylist
import com.example.mymusic.core.database.model.entities.LocalSimplifiedArtist
import com.example.mymusic.core.database.model.entities.LocalSimplifiedTrack
import com.example.mymusic.core.database.model.entities.LocalTrack
import com.example.mymusic.core.database.model.crossRef.AlbumArtistCrossRef
import com.example.mymusic.core.database.model.crossRef.AlbumTrackCrossRef
import com.example.mymusic.core.database.model.crossRef.PlaylistTrackCrossRef
import com.example.mymusic.core.database.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.mymusic.core.database.model.crossRef.TrackArtistCrossRef
import com.example.mymusic.core.database.model.entities.CursorRemoteKeys
import com.example.mymusic.core.database.model.entities.RemoteKeys


@Database(entities = [
    LocalArtist::class,
    LocalSimplifiedArtist::class,
    LocalAlbum::class,
    LocalTrack::class,
    TrackArtistCrossRef::class,
    AlbumArtistCrossRef::class,
    AlbumTrackCrossRef::class,
    SimplifiedTrackArtistCrossRef::class,
    LocalRecommendation::class,
    LocalRecentlyPlayed::class,
    LocalSimplifiedTrack::class,
    LocalSavedAlbum::class,
    LocalPlaylist::class,
    LocalSavedPlaylist::class,
    PlaylistTrackCrossRef::class,
    CursorRemoteKeys::class,
    RemoteKeys::class],
    version = 1,
    exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun musicDao(): MusicDao

    abstract fun remoteKeysDao(): RemoteKeysDao
}