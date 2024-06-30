package com.example.mymusic.core.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Cross reference for many-to-many relationship between [LocalSimplifiedArtist] and [LocalAlbum].
 * Foreign keys were added for propagating updates to the child keys. onDelete is set to NO_ACTION
 * because there can be other dependencies (like playlists, singles, etc.)
 */
@Entity(
    primaryKeys = ["simplifiedArtistId", "albumId"],
//    foreignKeys = [
//        ForeignKey(
//            entity = LocalSimplifiedArtist::class,
//            parentColumns = arrayOf("simplifiedArtistId"),
//            childColumns = arrayOf("simplifiedArtistId"),
//            onUpdate = ForeignKey.CASCADE,
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = LocalAlbum::class,
//            parentColumns = arrayOf("albumId"),
//            childColumns = arrayOf("albumId"),
//            onUpdate = ForeignKey.CASCADE,
//            onDelete = ForeignKey.CASCADE
//        )],
    indices = [
        Index(value = ["simplifiedArtistId"]),
        Index(value = ["albumId"])
    ]
)
data class AlbumArtistCrossRef(
    val simplifiedArtistId: String,
    val albumId: String
)