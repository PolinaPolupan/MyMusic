package com.example.mymusic.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.mymusic.model.Artist
import com.example.mymusic.model.SimplifiedArtist
import com.example.mymusic.model.Track

@Entity(
    tableName = "tracks",
    foreignKeys = [ForeignKey(
        entity = LocalAlbum::class,
        parentColumns = arrayOf("albumId"),
        childColumns = arrayOf("trackId"),
        onUpdate = ForeignKey.CASCADE
    )]
)
data class LocalTrack(
    @ColumnInfo(name = "trackId")
    @PrimaryKey val id: String,
    @Embedded val album: LocalAlbum,
    @ColumnInfo(name = "trackName") val name: String
)

fun LocalTrack.toExternal(albumArtists: List<SimplifiedArtist>, artists: List<Artist>) = Track(
    id = id,
    album = album.toExternal(albumArtists),
    name = name,
    artists = artists
)