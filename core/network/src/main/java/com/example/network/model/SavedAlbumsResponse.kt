package com.example.network.model

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


