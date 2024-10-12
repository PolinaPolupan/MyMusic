package com.example.mymusic.core.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * [LocalRecentlyPlayedWithArtists] defines a track, which was recently played.
 * The tracks are stored in the separate table in the database, because these are
 * retrieved from the API and saved in the sorted order.
 * https://developer.spotify.com/documentation/web-api/reference/get-recently-played
 * TODO: Make playHistory table save only id of tracks and key by which these will be sorted
 */
@Entity(
    tableName = "recently_played",
    foreignKeys = [ForeignKey(
        entity = LocalTrack::class,
        parentColumns = arrayOf("trackId"),
        childColumns = arrayOf("recentlyPlayedId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class LocalRecentlyPlayed(
    @ColumnInfo(name = "recentlyPlayedId") @PrimaryKey val id: String,
    @Embedded val track: LocalTrack
)