package com.example.mymusic.core.data.network.model

import com.example.mymusic.core.data.local.model.entities.LocalAlbum
import com.example.mymusic.core.data.local.model.entities.LocalSavedAlbum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SavedAlbum(
    @SerialName("added_at")
    val addedAt: String,
    val album: SpotifyAlbum
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

fun SavedAlbum.toLocalAlbum() = LocalAlbum(
    id = album.id,
    type = album.type,
    imageUrl = if (album.images.isNotEmpty()) album.images[0].url else "",
    name = album.name
)

fun List<SavedAlbum>.toLocalAlbum() = map(SavedAlbum::toLocalAlbum)

fun SavedAlbum.toLocal() = LocalSavedAlbum(
    id = album.id
)

fun List<SavedAlbum>.toLocal() = map(SavedAlbum::toLocal)

