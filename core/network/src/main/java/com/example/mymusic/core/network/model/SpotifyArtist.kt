package com.example.network.model

import com.example.database.model.entities.LocalArtist
import com.example.database.model.entities.LocalSimplifiedArtist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyArtist(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls?,
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

@JvmName("SpotifyArtistToLocalArtist")
fun SpotifyArtist.toLocal() = LocalArtist(
    id = id,
    name = name,
    imageUrl = if (images?.isNotEmpty() == true) images!![0].url else ""
)

@JvmName("SpotifyArtistListToLocalArtists")
fun List<SpotifyArtist>.toLocal() = map(SpotifyArtist::toLocal)

@JvmName("SpotifyArtistToLocalSimplifiedArtist")
fun SpotifyArtist.toLocalSimplified() = LocalSimplifiedArtist(
    id = id,
    name = name
)

@JvmName("SpotifyArtistListToLocalSimplifiedArtists")
fun List<SpotifyArtist>.toLocalSimplified() = map(SpotifyArtist::toLocalSimplified)
