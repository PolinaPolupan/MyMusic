package com.example.mymusic.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UsersTopItemsResponse(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: String,
    val items: List<SpotifyTrack> // OneOf issue https://developer.spotify.com/documentation/web-api/reference/get-users-top-artists-and-tracks
)