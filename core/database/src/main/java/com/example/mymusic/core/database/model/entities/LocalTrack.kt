package com.example.mymusic.core.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import com.example.mymusic.core.model.Artist
import com.example.mymusic.core.model.SimplifiedArtist
import com.example.mymusic.core.model.Track


/**
 * [LocalTrack] defines a track.
 * It has many-to-many relationships with [LocalArtist]
 */
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED) // Is used to suppress warnings because of embedded album field
@Entity(tableName = "tracks")
data class LocalTrack(
    @ColumnInfo(name = "trackId")
    @PrimaryKey val id: String,
    @Embedded val album: LocalAlbum,
    @ColumnInfo(name = "trackName") val name: String,
    val uri: String
)

fun LocalTrack.toExternal(albumArtists: List<SimplifiedArtist>, artists: List<Artist>) = Track(
    id = id,
    album = album.toExternalSimplified(albumArtists),
    name = name,
    artists = artists,
    uri = uri
)