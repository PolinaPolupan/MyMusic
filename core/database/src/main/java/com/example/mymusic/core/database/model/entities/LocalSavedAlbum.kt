package com.example.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_albums")
data class LocalSavedAlbum(
    @ColumnInfo(name = "savedAlbumId")
    @PrimaryKey val id: String
)
