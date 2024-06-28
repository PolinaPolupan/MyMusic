package com.example.mymusic.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [
    LocalArtist::class,
    LocalSimplifiedArtist::class,
    LocalAlbum::class,
    LocalTrack::class,
    TrackArtistCrossRef::class,
    AlbumArtistCrossRef::class],
    version = 1,
    exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun musicDao(): MusicDao
}