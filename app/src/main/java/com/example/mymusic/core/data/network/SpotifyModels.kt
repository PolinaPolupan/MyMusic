package com.example.mymusic.core.data.network

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
data class SpotifySimplifiedArtist(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)

@Serializable
data class SpotifyArtist(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val followers: Followers? = null,
    val genres: List<String>? = null,
    val href: String,
    val id: String,
    val images: List<SpotifyImage>? = null,
    val name: String,
    val popularity: Int? = null,
    val type: String,
    val uri: String
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
data class SpotifyAlbum(
    @SerialName("album_type")
    val albumType: String,
    @SerialName("total_tracks")
    val totalTracks: Int,
    @SerialName("available_markets")
    val availableMarkets: List<String>,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<SpotifyImage>,
    val name: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("release_date_precision")
    val releaseDatePrecision: String,
    val restrictions: Restrictions? = null,
    val type: String,
    val uri: String,
    val artists: List<SpotifySimplifiedArtist>
)

@Serializable
data class SpotifyTrack(
    val album: SpotifyAlbum,
    val artists: List<SpotifyArtist>,
    @SerialName("available_markets")
    val availableMarkets: List<String>,
    @SerialName("disc_number")
    val discNumber: Int,
    @SerialName("duration_ms")
    val durationMs: Int,
    val explicit: Boolean,
    @SerialName("external_ids")
    val externalIds: ExternalIds,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    @SerialName("is_playable")
    val isPlayable: Boolean? = null,
    @SerialName("linked_from")
    val linkedFrom: LinkedFrom? = null,
    val restrictions: Restrictions? = null,
    val name: String,
    val popularity: Int,
    @SerialName("preview_url")
    val previewUrl: String?,
    @SerialName("track_number")
    val trackNumber: Int,
    val type: String,
    val uri: String,
    @SerialName("is_local")
    val isLocal: Boolean
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
data class ErrorResponse(val message: String)