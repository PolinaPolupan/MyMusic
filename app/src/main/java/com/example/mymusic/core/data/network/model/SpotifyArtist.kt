package com.example.mymusic.core.data.network.model

import com.example.mymusic.model.Artist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

fun SpotifyArtist.toExternal() = Artist(
    id = id,
    name = name,
    imageUrl = if (images?.isNotEmpty() == true) images[0].url else ""
)