package com.example.mymusic.core.data.local.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymusic.model.SimplifiedArtist

@Entity(tableName = "simplified_artists")
data class LocalSimplifiedArtist(
    @ColumnInfo(name = "simplifiedArtistId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "simplifiedArtistName") val name: String
)

fun LocalSimplifiedArtist.toExternal() = SimplifiedArtist(
    id = id,
    name = name
)

fun List<LocalSimplifiedArtist>.toExternal() = map(LocalSimplifiedArtist::toExternal)