package com.example.mymusic.core.data.network.model

import com.example.mymusic.model.Album
import com.example.mymusic.model.AlbumType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

fun SpotifyAlbum.toExternal() = Album(
    id = id,
    type = type.toAlbumType(),
    imageUrl = if (images.isNotEmpty()) images[0].url else "",
    name = name,
    artists = artists.map { it.toExternal() }
)

fun String.toAlbumType(): AlbumType {
    return when(this) {
        "album" -> AlbumType.Album
        "single" -> AlbumType.Single
        "compilation" -> AlbumType.Compilation
        else -> {
            throw IllegalArgumentException("Unknown type")
        }
    }
}