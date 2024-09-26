package com.example.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.SimplifiedArtist

@Entity(tableName = "simplified_artists")
data class LocalSimplifiedArtist(
    @ColumnInfo(name = "simplifiedArtistId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "simplifiedArtistName") val name: String
)

@JvmName("LocalSimplifiedArtistToExternalSimplifiedArtist")
fun LocalSimplifiedArtist.toExternal() = SimplifiedArtist(
    id = id,
    name = name
)

@JvmName("LocalSimplifiedArtistListToExternalSimplifiedArtists")
fun List<LocalSimplifiedArtist>.toExternal() = map(LocalSimplifiedArtist::toExternal)