package com.example.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AlbumTracksResponse(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SpotifySimplifiedTrack>
)