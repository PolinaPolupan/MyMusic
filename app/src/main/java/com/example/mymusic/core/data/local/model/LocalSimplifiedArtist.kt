package com.example.mymusic.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.mymusic.model.SimplifiedArtist

@Entity(
    tableName = "simplifiedArtists",
    foreignKeys = [ForeignKey(
        entity = LocalArtist::class,
        parentColumns = arrayOf("artistId"),
        childColumns = arrayOf("simplifiedArtistId"),
        onUpdate = ForeignKey.CASCADE
    )]
)
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