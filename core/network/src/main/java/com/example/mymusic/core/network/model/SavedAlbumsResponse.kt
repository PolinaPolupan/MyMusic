package com.example.network.model

import com.example.database.model.entities.LocalAlbum
import com.example.database.model.entities.LocalSavedAlbum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SavedAlbum(
    @SerialName("added_at")
    val addedAt: String,
    val album: SpotifyAlbumExtended
)

@Serializable
data class SavedAlbumsResponse(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SavedAlbum>
)

@JvmName("SavedAlbumToLocalAlbumConversion")
fun SavedAlbum.toLocalAlbum() = LocalAlbum(
    id = album.id,
    type = album.type,
    imageUrl = if (album.images.isNotEmpty()) album.images[0].url else "",
    name = album.name
)

@JvmName("SavedAlbumListToLocalAlbums")
fun List<SavedAlbum>.toLocalAlbum() = map(SavedAlbum::toLocalAlbum)

@JvmName("SavedAlbumToLocalSavedAlbum")
fun SavedAlbum.toLocal() = LocalSavedAlbum(
    id = album.id
)

@JvmName("SavedAlbumListToLocalSavedAlbums")
fun List<SavedAlbum>.toLocal() = map(SavedAlbum::toLocal)
