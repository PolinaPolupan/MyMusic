package com.example.mymusic.core.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class LocalAlbumWithArtists(
    @Embedded val album: LocalAlbum,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "simplifiedArtistId",
        associateBy = Junction(AlbumArtistCrossRef::class)
    )
    val simplifiedArtists: List<LocalSimplifiedArtist>
)