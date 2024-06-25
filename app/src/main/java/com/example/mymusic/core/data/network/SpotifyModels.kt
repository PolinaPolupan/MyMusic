package com.example.mymusic.core.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("display_name")
    val displayName: String,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<Image>,
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
data class Image(
    val url: String,
    val height: Int?,
    val width: Int?
)

@Serializable
data class Restrictions(
    val reasons: String
)

@Serializable
data class SimplifiedArtist(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)

@Serializable
data class Artist(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val followers: Followers? = null,
    val genres: List<String>? = null,
    val href: String,
    val id: String,
    val images: List<Image>,
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
data class Album(
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
    val images: List<Image>,
    val name: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("release_date_precision")
    val releaseDatePrecision: String,
    val restrictions: Restrictions? = null,
    val type: String,
    val uri: String,
    val artists: List<SimplifiedArtist>
)

@Serializable
data class Track(
    val album: Album,
    val artists: List<Artist>,
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