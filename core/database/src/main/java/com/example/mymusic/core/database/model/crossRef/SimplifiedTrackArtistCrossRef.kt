package com.example.mymusic.core.database.model.crossRef

import androidx.room.Entity
import androidx.room.Index


/**
 * [SimplifiedTrackArtistCrossRef] defined many-to-many relationship between
 * [LocalSimplifiedArtist] and [LocalSimplifiedTrack]
 */
@Entity(
    tableName = "simplified_track_artist",
    primaryKeys = ["simplifiedTrackId", "simplifiedArtistId"],
    indices = [
        Index(value = ["simplifiedTrackId"]),
        Index(value = ["simplifiedArtistId"])
    ]
)
data class SimplifiedTrackArtistCrossRef(
    val simplifiedTrackId: String,
    val simplifiedArtistId: String
)