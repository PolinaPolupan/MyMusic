package com.example.network.model

import com.example.database.model.entities.LocalSimplifiedTrack
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifySimplifiedTrack(
    val artists: List<SpotifySimplifiedArtist>,
    @SerialName("available_markets")
    val availableMarkets: List<String>? = null,
    @SerialName("disc_number")
    val discNumber: Int,
    @SerialName("duration_ms")
    val durationMs: Int,
    val explicit: Boolean,
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
    @SerialName("preview_url")
    val previewUrl: String?,
    @SerialName("track_number")
    val trackNumber: Int,
    val type: String,
    val uri: String,
    @SerialName("is_local")
    val isLocal: Boolean
)

@JvmName("SpotifySimplifiedTrackToLocalSimplifiedTrack")
fun SpotifySimplifiedTrack.toLocal() = LocalSimplifiedTrack(
    id = id,
    name = name
)

@JvmName("SpotifySimplifiedTrackListToLocalSimplifiedTracks")
fun List<SpotifySimplifiedTrack>.toLocal() = map(SpotifySimplifiedTrack::toLocal)
