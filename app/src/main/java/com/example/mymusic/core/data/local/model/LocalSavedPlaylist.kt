package com.example.mymusic.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_playlists")
data class LocalSavedPlaylist(
    @ColumnInfo("savedPlaylistId")
    @PrimaryKey val id: String,
)