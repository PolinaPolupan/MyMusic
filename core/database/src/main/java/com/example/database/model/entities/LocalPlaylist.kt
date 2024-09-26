package com.example.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.SimplifiedPlaylist

@Entity(tableName = "playlists")
data class LocalPlaylist(
    @ColumnInfo("playlistId")
    @PrimaryKey val id: String,
    val imageUrl: String,
    val name: String,
    val ownerName: String?
)

@JvmName("LocalPlaylistToExternalSimplifiedPlaylist")
fun LocalPlaylist.toExternal() = SimplifiedPlaylist(
    id = id,
    imageUrl = imageUrl,
    name = name,
    ownerName = ownerName ?: ""
)
