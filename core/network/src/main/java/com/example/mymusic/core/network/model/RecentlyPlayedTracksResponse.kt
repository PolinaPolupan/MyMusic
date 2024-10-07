package com.example.mymusic.core.network.model

import com.example.database.model.entities.LocalRecentlyPlayed
import com.example.network.model.Context
import com.example.network.model.Cursors
import com.example.network.model.SpotifyTrack
import com.example.network.model.toLocalTrack
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyPlayHistoryObject(
    val track: SpotifyTrack,
    @SerialName("played_at") val playedAt: String?,
    val context: Context?
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

@JvmName("SpotifyPlayHistoryObjectToLocalRecentlyPlayed")
fun SpotifyPlayHistoryObject.toLocal() = LocalRecentlyPlayed(
    id = track.id,
    track = track.toLocalTrack()
)

@JvmName("SpotifyPlayHistoryObjectListToLocalRecentlyPlayed")
fun List<SpotifyPlayHistoryObject>.toLocal() = map(SpotifyPlayHistoryObject::toLocal)
