package com.example.mymusic.core.data.local.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["artistId", "trackId"],
    indices = [
        Index(value = ["artistId"]),
        Index(value = ["trackId"])
    ]
)
data class TrackArtistCrossRef(
    val artistId: String,
    val trackId: String
)