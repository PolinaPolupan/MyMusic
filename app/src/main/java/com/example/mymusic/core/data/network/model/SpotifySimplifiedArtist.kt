package com.example.mymusic.core.data.network.model

import com.example.mymusic.model.SimplifiedArtist
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

fun SpotifySimplifiedArtist.toExternal() = SimplifiedArtist(
    id = id,
    name = name
)