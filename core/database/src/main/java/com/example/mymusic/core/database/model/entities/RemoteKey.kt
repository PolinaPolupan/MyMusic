package com.example.mymusic.core.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cursor_remote_keys")
data class CursorRemoteKeys(
    @ColumnInfo(name = "cursorId") @PrimaryKey val id: String,
    val before: Long?,
    val after: Long?
)

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @ColumnInfo(name = "keyId") @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)