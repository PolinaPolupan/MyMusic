package com.example.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class LocalArtist(
    @ColumnInfo(name = "artistId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "artistName") val name: String,
    @ColumnInfo(name = "artistImageUrl") val imageUrl: String?
)