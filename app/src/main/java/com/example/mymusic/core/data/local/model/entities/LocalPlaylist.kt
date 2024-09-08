package com.example.mymusic.core.data.local.model.entities

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

fun LocalPlaylist.toExternal() = com.example.model.SimplifiedPlaylist(
    id = id,
    imageUrl = imageUrl,
    name = name,
    ownerName = ownerName ?: ""
)

fun List<LocalPlaylist>.toExternal() = map(LocalPlaylist::toExternal)