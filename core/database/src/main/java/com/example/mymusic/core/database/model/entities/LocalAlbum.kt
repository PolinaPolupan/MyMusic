package com.example.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymusic.core.model.SimplifiedAlbum
import com.example.mymusic.core.model.SimplifiedArtist

@Entity(tableName = "albums")
data class LocalAlbum(
    @ColumnInfo(name = "albumId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "albumType") val type: String,
    @ColumnInfo(name = "albumImageUrl") val imageUrl: String,
    @ColumnInfo(name = "albumName") val name: String,
)

@JvmName("StringToAlbumTypeConversion")
fun String.toAlbumType(): com.example.mymusic.core.model.AlbumType {
    return when(this) {
        "album" -> com.example.mymusic.core.model.AlbumType.Album
        "single" -> com.example.mymusic.core.model.AlbumType.Single
        "compilation" -> com.example.mymusic.core.model.AlbumType.Compilation
        else -> {
            throw IllegalArgumentException("Unknown type")
        }
    }
}

@JvmName("LocalAlbumToExternalSimplifiedAlbum")
fun LocalAlbum.toExternalSimplified(artists: List<com.example.mymusic.core.model.SimplifiedArtist>): com.example.mymusic.core.model.SimplifiedAlbum =
    com.example.mymusic.core.model.SimplifiedAlbum(
        id = id,
        type = type.toAlbumType(),
        imageUrl = imageUrl,
        name = name,
        artists = artists
    )
