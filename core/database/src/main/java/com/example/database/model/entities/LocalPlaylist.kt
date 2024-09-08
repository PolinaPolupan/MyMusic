package com.example.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class LocalPlaylist(
    @ColumnInfo("playlistId")
    @PrimaryKey val id: String,
    val imageUrl: String,
    val name: String,
    val ownerName: String?
)
