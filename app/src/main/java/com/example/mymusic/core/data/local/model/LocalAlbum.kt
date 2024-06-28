package com.example.mymusic.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymusic.core.data.network.model.toAlbumType
import com.example.mymusic.model.Album
import com.example.mymusic.model.SimplifiedArtist

@Entity(tableName = "albums")
data class LocalAlbum(
    @ColumnInfo(name = "albumId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "albumType") val type: String,
    @ColumnInfo(name = "albumImageUrl") val imageUrl: String,
    @ColumnInfo(name = "albumName") val name: String,
)

fun LocalAlbum.toExternal(artists: List<SimplifiedArtist>) = Album(
    id = id,
    type = type.toAlbumType(),
    imageUrl = imageUrl,
    name = name,
    artists = artists
)