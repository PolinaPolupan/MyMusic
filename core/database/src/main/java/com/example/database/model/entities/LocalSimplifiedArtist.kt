package com.example.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "simplified_artists")
data class LocalSimplifiedArtist(
    @ColumnInfo(name = "simplifiedArtistId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "simplifiedArtistName") val name: String
)
