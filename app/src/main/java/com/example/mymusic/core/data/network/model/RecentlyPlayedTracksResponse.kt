package com.example.mymusic.core.data.network.model

import com.example.mymusic.core.data.local.model.entities.LocalRecentlyPlayed
import com.example.mymusic.core.data.local.model.entities.LocalSimplifiedTrack
import com.example.mymusic.core.data.local.model.entities.LocalTrack
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

fun SpotifyPlayHistoryObject.toLocal() = LocalRecentlyPlayed(
    id = track.id,
    track = track.toLocalTrack()
)

fun SpotifyPlayHistoryObject.toLocalTrack() = LocalTrack(
    id = track.id,
    album = track.album.toLocal(),
    name = track.name
)

fun SpotifyPlayHistoryObject.toLocalSimplifiedTrack() = LocalSimplifiedTrack(
    id = track.id,
    name = track.name
)

fun List<SpotifyPlayHistoryObject>.toLocalTracks() = map(SpotifyPlayHistoryObject::toLocalTrack)

fun List<SpotifyPlayHistoryObject>.toLocalSimplifiedTracks() = map(SpotifyPlayHistoryObject::toLocalSimplifiedTrack)

fun List<SpotifyPlayHistoryObject>.toLocal() = map(SpotifyPlayHistoryObject::toLocal)