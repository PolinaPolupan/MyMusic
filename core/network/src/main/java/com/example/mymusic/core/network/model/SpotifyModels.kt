package com.example.network.model

import androidx.room.ColumnInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    val reason: String
)

@Serializable
data class ExternalIds(
    val isrc: String? = null,
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
    val href: String?,
    val id: String,
    val initialPoolSize: Int,
    val type: String
)

@Serializable
data class RecommendationsResponse(
    val tracks: List<SpotifyTrack>,
    val seeds: List<RecommendationSeed>
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

@Serializable
data class SpotifyOwner(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls?,
    val followers: Followers? = null,
    val href: String,
    val id: String,
    val type: String,
    val uri: String,
    @SerialName("display_name")
    val displayName: String?
)

@Serializable
data class SpotifyTracks(
    val href: String,
    val total: Int
)

data class ErrorResponse(val message: String)