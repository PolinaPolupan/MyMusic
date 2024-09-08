package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifySimplifiedPlaylist(
    val collaborative: Boolean,
    val description: String,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String,
    val id: String,
    val images: List<SpotifyImage>?,
    val name: String,
    val owner: SpotifyOwner,
    val public: Boolean,
    @SerialName("snapshot_id")
    val snapshotId: String?,
    val tracks: SpotifyTracks,
    val type: String,
    val uri: String
)
