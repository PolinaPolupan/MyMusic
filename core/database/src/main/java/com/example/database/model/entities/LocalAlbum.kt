package com.example.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class LocalAlbum(
    @ColumnInfo(name = "albumId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "albumType") val type: String,
    @ColumnInfo(name = "albumImageUrl") val imageUrl: String,
    @ColumnInfo(name = "albumName") val name: String,
)
