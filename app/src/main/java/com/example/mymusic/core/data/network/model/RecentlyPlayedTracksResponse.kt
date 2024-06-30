package com.example.mymusic.core.data.network.model

import com.example.mymusic.core.data.local.model.LocalPlayHistory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyPlayHistoryObject(
    val track: SpotifyTrack,
    @SerialName("played_at") val playedAt: String?,
    val context: Context
)

@Serializable
data class RecentlyPlayedTracksResponse(
    val href: String,
    val limit: Int,
    val next: String?,
    val cursors: Cursors,
    val total: Int? = null,
    val items: List<SpotifyPlayHistoryObject>
)

fun SpotifyPlayHistoryObject.toLocal() = LocalPlayHistory(
    id = track.id,
    playedAt = playedAt,
    track = track.toLocalTrack()
)

fun List<SpotifyPlayHistoryObject>.toLocal() = map(SpotifyPlayHistoryObject::toLocal)
