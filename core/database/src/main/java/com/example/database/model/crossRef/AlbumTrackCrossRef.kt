package com.example.database.model.crossRef

import androidx.room.Entity
import androidx.room.Index

/**
 * [AlbumTrackCrossRef] is a cross reference which defines one-to-many relationship between
 * [LocalAlbum] and [LocalSimplifiedTrack]
 */
@Entity(
    tableName = "album_track",
    primaryKeys = ["simplifiedTrackId", "albumId"],
    indices = [
        Index(value = ["simplifiedTrackId"]),
        Index(value = ["albumId"])
    ]
)
data class AlbumTrackCrossRef(
    val simplifiedTrackId: String,
    val albumId: String
)