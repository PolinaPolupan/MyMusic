package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddedBy(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val followers: Followers? = null,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)

/*TODO: What if there is a podcast in the response*/
@Serializable
data class PlaylistTrack(
    @SerialName("added_at")
    val addedAt: String,
    @SerialName("added_by")
    val addedBy: AddedBy,
    @SerialName("is_local")
    val isLocal: Boolean,
    val track: SpotifyTrack
)

@Serializable
data class PlaylistsTracksResponse(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<PlaylistTrack>
)
