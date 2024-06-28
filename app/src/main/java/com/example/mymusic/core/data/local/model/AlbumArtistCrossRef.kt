package com.example.mymusic.core.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["simplifiedArtistId", "albumId"])
data class AlbumArtistCrossRef(
    val simplifiedArtistId: String,
    val albumId: String
)