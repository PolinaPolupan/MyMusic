package com.example.mymusic.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymusic.model.SimplifiedPlaylist

@Entity(tableName = "saved_playlists")
data class LocalSavedPlaylist(
    @ColumnInfo("savedPlaylistId")
    @PrimaryKey val id: String,
    val imageUrl: String,
    val name: String,
    val ownerName: String?
)

fun LocalSavedPlaylist.toExternal() = SimplifiedPlaylist(
    id = id,
    imageUrl = imageUrl,
    name = name,
    ownerName = ownerName ?: ""
)

fun List<LocalSavedPlaylist>.toExternal() = map(LocalSavedPlaylist::toExternal)