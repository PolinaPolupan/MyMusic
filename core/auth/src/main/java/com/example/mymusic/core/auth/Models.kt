package com.example.mymusic.core.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyUser(
    @SerialName("display_name")
    val displayName: String,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<SpotifyImage>,
    val type: String,
    val uri: String,
    val followers: Followers,
    val country: String,
    val product: String,
    @SerialName("explicit_content")
    val explicitContent: ExplicitContent,
    val email: String,
)

@Serializable
data class ExplicitContent(
    @SerialName("filter_enabled")
    val filterEnabled: Boolean,
    @SerialName("filter_locked")
    val filterLocked: Boolean
)

@Serializable
data class ExternalUrls(
    val spotify: String
)

@Serializable
data class Followers(
    val href: String?,
    val total: Int
)

@Serializable
data class SpotifyImage(
    val url: String,
    val height: Int?,
    val width: Int?
)
