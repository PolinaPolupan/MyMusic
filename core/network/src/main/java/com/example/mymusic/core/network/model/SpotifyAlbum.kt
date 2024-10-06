package com.example.network.model

import com.example.database.model.entities.LocalAlbum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyAlbum(
    @SerialName("album_type")
    val albumType: String?,
    @SerialName("total_tracks")
    val totalTracks: Int,
    @SerialName("available_markets")
    val availableMarkets: List<String>?,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String,
    val id: String,
    val images: List<SpotifyImage>,
    val name: String,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("release_date_precision")
    val releaseDatePrecision: String?,
    val restrictions: Restrictions? = null,
    val type: String,
    val uri: String,
    val artists: List<SpotifySimplifiedArtist>
)

@JvmName("SpotifyAlbumToLocalAlbum")
fun SpotifyAlbum.toLocal() = LocalAlbum(
    id = id,
    type = type,
    imageUrl = if (images.isNotEmpty()) images[0].url else "",
    name = name
)

