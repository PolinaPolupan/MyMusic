package com.example.mymusic.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymusic.core.data.local.model.AlbumArtistCrossRef
import com.example.mymusic.core.data.local.model.LocalAlbum
import com.example.mymusic.core.data.local.model.LocalArtist
import com.example.mymusic.core.data.local.model.LocalPlayHistory
import com.example.mymusic.core.data.local.model.LocalSimplifiedArtist
import com.example.mymusic.core.data.local.model.LocalTrack
import com.example.mymusic.core.data.local.model.LocalRecommendation
import com.example.mymusic.core.data.local.model.TrackArtistCrossRef

@Database(entities = [
    LocalArtist::class,
    LocalSimplifiedArtist::class,
    LocalAlbum::class,
    LocalTrack::class,
    TrackArtistCrossRef::class,
    AlbumArtistCrossRef::class,
    LocalRecommendation::class,
    LocalPlayHistory::class],
    version = 39,
    exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun musicDao(): MusicDao
}