package com.example.mymusic.core.data.local.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @ColumnInfo(name = "recentlyPlayedId") @PrimaryKey val id: String,
    val prevKey: Long?,
    val nextKey: Long?
)
