package com.example.mymusic.core.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["artistId", "trackId"])
data class TrackArtistCrossRef(
    val artistId: String,
    val trackId: String
)