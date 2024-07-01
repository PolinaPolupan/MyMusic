package com.example.mymusic.core.data.network.model

import com.example.mymusic.core.data.local.model.LocalSimplifiedArtist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

fun SpotifySimplifiedArtist.toLocal() = LocalSimplifiedArtist(
    id = id,
    name = name
)

fun List<SpotifySimplifiedArtist>.toLocal() = map(SpotifySimplifiedArtist::toLocal)