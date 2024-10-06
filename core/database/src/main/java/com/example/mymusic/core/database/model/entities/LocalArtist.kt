package com.example.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymusic.core.model.Artist

@Entity(tableName = "artists")
data class LocalArtist(
    @ColumnInfo(name = "artistId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "artistName") val name: String,
    @ColumnInfo(name = "artistImageUrl") val imageUrl: String?
)

@JvmName("LocalArtistToExternalArtist")
fun LocalArtist.toExternal() = com.example.mymusic.core.model.Artist(
    id = id,
    name = name,
    imageUrl = imageUrl
)

@JvmName("LocalArtistListToExternalArtists")
fun List<LocalArtist>.toExternal() = map(LocalArtist::toExternal)