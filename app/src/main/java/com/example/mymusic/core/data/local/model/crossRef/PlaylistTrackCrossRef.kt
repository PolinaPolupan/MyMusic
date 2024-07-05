package com.example.mymusic.core.data.local.model.crossRef

import androidx.room.Entity
import androidx.room.Index

/**
 * [PlaylistTrackCrossRef] is a cross reference which defines many-to-many relationship between
 * [LocalSavedPlaylist] and [LocalTrack]
 */
@Entity(
    tableName = "playlist_track",
    primaryKeys = ["trackId", "playlistId"],
    indices = [
        Index(value = ["trackId"]),
        Index(value = ["playlistId"])
    ]
)
data class PlaylistTrackCrossRef(
    val playlistId: String,
    val trackId: String
)
