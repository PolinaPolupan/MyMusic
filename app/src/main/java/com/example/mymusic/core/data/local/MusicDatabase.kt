package com.example.mymusic.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymusic.core.data.local.model.crossRef.AlbumArtistCrossRef
import com.example.mymusic.core.data.local.model.crossRef.AlbumTrackCrossRef
import com.example.mymusic.core.data.local.model.entities.LocalAlbum
import com.example.mymusic.core.data.local.model.entities.LocalArtist
import com.example.mymusic.core.data.local.model.entities.LocalPlaylist
import com.example.mymusic.core.data.local.model.entities.LocalSavedPlaylist
import com.example.mymusic.core.data.local.model.entities.LocalRecentlyPlayed
import com.example.mymusic.core.data.local.model.entities.LocalSimplifiedArtist
import com.example.mymusic.core.data.local.model.entities.LocalTrack
import com.example.mymusic.core.data.local.model.entities.LocalRecommendation
import com.example.mymusic.core.data.local.model.entities.LocalSavedAlbum
import com.example.mymusic.core.data.local.model.entities.LocalSimplifiedTrack
import com.example.mymusic.core.data.local.model.crossRef.PlaylistTrackCrossRef
import com.example.mymusic.core.data.local.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.mymusic.core.data.local.model.crossRef.TrackArtistCrossRef
import com.example.mymusic.core.data.local.model.entities.CursorRemoteKeys
import com.example.mymusic.core.data.local.model.entities.RemoteKeys

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