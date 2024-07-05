package com.example.mymusic.core.data.local.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [LocalSimplifiedTrack] defines a track without any link to the album
 */
@Entity(tableName = "simplified_tracks")
data class LocalSimplifiedTrack(
    @ColumnInfo(name = "simplifiedTrackId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "simplifiedTrackName") val name: String
)