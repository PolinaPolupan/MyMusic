package com.example.mymusic.core.data.network.model

import com.example.mymusic.model.Track
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

@Serializable
data class Restrictions(
    val reasons: String
)

@Serializable
data class ExternalIds(
    val isrc: String,
    val ean: String? = null,
    val upc: String? = null
)

@Serializable
data class LinkedFrom(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val type: String,
    val uri: String,
)

@Serializable
data class RecommendationSeed(
    val afterFilteringSize: Int,
    val afterRelinkingSize: Int,
    val href: String,
    val id: String,
    val initialPoolSize: Int,
    val type: String
)

@Serializable
data class RecommendationsResponse(
    val seeds: List<RecommendationSeed>,
    val tracks: List<SpotifyTrack>
)

@Serializable
data class Cursors(
    val after: String,
    val before: String
)

@Serializable
data class Context(
    val type: String,
    val href: String,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val uri: String
)

fun RecommendationsResponse.toExternal(): List<Track> = this.tracks.map { it.toExternal() }

data class ErrorResponse(val message: String)