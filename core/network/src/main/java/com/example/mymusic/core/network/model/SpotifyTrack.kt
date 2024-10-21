package com.example.mymusic.core.network.model

import com.example.mymusic.core.database.model.entities.LocalRecommendation
import com.example.mymusic.core.database.model.entities.LocalSimplifiedTrack
import com.example.mymusic.core.database.model.entities.LocalTrack
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyTrack(
    val album: SpotifyAlbum,
    val artists: List<SpotifyArtist>,
    @SerialName("available_markets")
    val availableMarkets: List<String>?,
    @SerialName("disc_number")
    val discNumber: Int,
    @SerialName("duration_ms")
    val durationMs: Int,
    val explicit: Boolean,
    @SerialName("external_ids")
    val externalIds: ExternalIds?,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls?,
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
    val isLocal: Boolean,
    val episode: Boolean? = null,
    val track: Boolean? = null
)

@JvmName("SpotifyTrackToLocalTrackConversion")
fun SpotifyTrack.toLocalTrack() = LocalTrack(
    id = id,
    album = album.toLocal(),
    name = name
)

fun List<SpotifyTrack>.toLocalTracks() = map(SpotifyTrack::toLocalTrack)

@JvmName("SpotifyTrackToLocalSimplifiedTrackConversion")
fun SpotifyTrack.toLocalSimplifiedTrack() = LocalSimplifiedTrack(
    id = id,
    name = name
)

fun List<SpotifyTrack>.toLocalSimplifiedTracks() = map(SpotifyTrack::toLocalSimplifiedTrack)

@JvmName("SpotifyTrackToLocalRecommendation")
fun SpotifyTrack.toLocalRecommendation() = LocalRecommendation(
    id = id
)

@JvmName("SpotifyTrackListToLocalRecommendations")
fun List<SpotifyTrack>.toLocalRecommendations() = map(SpotifyTrack::toLocalRecommendation)