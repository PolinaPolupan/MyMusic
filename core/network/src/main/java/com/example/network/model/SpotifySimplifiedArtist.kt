package com.example.network.model

import com.example.database.model.entities.LocalSimplifiedArtist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifySimplifiedArtist(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)

@JvmName("SpotifySimplifiedArtistToLocalSimplifiedArtist")
fun SpotifySimplifiedArtist.toLocal() = LocalSimplifiedArtist(
    id = id,
    name = name
)

@JvmName("SpotifySimplifiedArtistListToLocalSimplifiedArtists")
fun List<SpotifySimplifiedArtist>.toLocal() = map(SpotifySimplifiedArtist::toLocal)
