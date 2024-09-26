package com.example.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.SimplifiedAlbum
import com.example.model.SimplifiedArtist

@Entity(tableName = "albums")
data class LocalAlbum(
    @ColumnInfo(name = "albumId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "albumType") val type: String,
    @ColumnInfo(name = "albumImageUrl") val imageUrl: String,
    @ColumnInfo(name = "albumName") val name: String,
)

@JvmName("StringToAlbumTypeConversion")
fun String.toAlbumType(): com.example.model.AlbumType {
    return when(this) {
        "album" -> com.example.model.AlbumType.Album
        "single" -> com.example.model.AlbumType.Single
        "compilation" -> com.example.model.AlbumType.Compilation
        else -> {
            throw IllegalArgumentException("Unknown type")
        }
    }
}

@JvmName("LocalAlbumToExternalSimplifiedAlbum")
fun LocalAlbum.toExternalSimplified(artists: List<SimplifiedArtist>): SimplifiedAlbum =
    SimplifiedAlbum(
        id = id,
        type = type.toAlbumType(),
        imageUrl = imageUrl,
        name = name,
        artists = artists
    )
