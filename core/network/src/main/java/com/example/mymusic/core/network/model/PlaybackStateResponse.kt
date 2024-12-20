package com.example.mymusic.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Device(
    val id: String?,
    @SerialName("is_active")
    val isActive: Boolean,
    @SerialName("is_private_session")
    val isPrivateSession: Boolean,
    @SerialName("is_restricted")
    val isRestricted: Boolean,
    val name: String,
    val type: String,
    @SerialName("volume_percent")
    val volumePercent: Int?,
    @SerialName("supports_volume")
    val supportsVolume: Boolean
)

@Serializable
data class Actions(
    @SerialName("interrupting_playback")
    val interruptingPlayback: Boolean,
    val pausing: Boolean,
    val resuming: Boolean,
    val seeking: Boolean,
    @SerialName("skipping_next")
    val skippingNext: Boolean,
    @SerialName("skipping_prev")
    val skippingPrev: Boolean,
    @SerialName("toggling_repeat_context")
    val togglingRepeatContext: Boolean,
    @SerialName("toggling_shuffle")
    val togglingShuffle: Boolean,
    @SerialName("toggling_repeat_track")
    val togglingRepeatTrack: Boolean,
    @SerialName("transferring_playback")
    val transferringPlayback: Boolean
)

@Serializable
data class PlaybackStateResponse(
    val device: Device,
    @SerialName("repeat_state")
    val repeatState: Boolean,
    @SerialName("shuffle_state")
    val shuffleState: Boolean,
    val context: Context,
    val timestamp: Int,
    @SerialName("progress_ms")
    val progressMs: Int,
    @SerialName("is_playing")
    val isPlaying: Boolean,
    val item: SpotifyTrack, // TODO: OneOf issue
    @SerialName("currently_playing_type")
    val currentlyPlayingType: String,
    val actions: Actions
)