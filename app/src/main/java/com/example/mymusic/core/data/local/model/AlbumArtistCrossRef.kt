package com.example.mymusic.core.data.local.model

import androidx.room.Entity
import androidx.room.Index

/**
 * Cross reference for many to many relationship between [LocalSimplifiedArtist] and [LocalAlbum]
 */
@Entity(
    primaryKeys = ["simplifiedArtistId", "albumId"],
    indices = [
        Index(value = ["simplifiedArtistId"]),
        Index(value = ["albumId"])
    ]
)
data class AlbumArtistCrossRef(
    val simplifiedArtistId: String,
    val albumId: String
)