package com.example.mymusic.core.data.local.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymusic.model.Artist

@Entity(tableName = "artists")
data class LocalArtist(
    @ColumnInfo(name = "artistId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "artistName") val name: String,
    @ColumnInfo(name = "artistImageUrl") val imageUrl: String?
)

fun LocalArtist.toExternal() = Artist(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun List<LocalArtist>.toExternal() = map(LocalArtist::toExternal)