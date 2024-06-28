package com.example.mymusic.core.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Cross reference for many-to-many relationship between [LocalArtist] and [LocalTrack].
 * Foreign keys were added for propagating updates to the child keys. onDelete is set to NO_ACTION
 * because there can be other dependencies (like playlists, singles, etc.)
 */
@Entity(
    primaryKeys = ["artistId", "trackId"],
    foreignKeys = [
        ForeignKey(
            entity = LocalTrack::class,
            parentColumns = arrayOf("trackId"),
            childColumns = arrayOf("trackId"),
            onUpdate = ForeignKey.CASCADE),
        ForeignKey(
            entity = LocalArtist::class,
            parentColumns = arrayOf("artistId"),
            childColumns = arrayOf("artistId"),
            onUpdate = ForeignKey.CASCADE)],
    indices = [
        Index(value = ["artistId"]),
        Index(value = ["trackId"])
    ]
)
data class TrackArtistCrossRef(
    val artistId: String,
    val trackId: String
)