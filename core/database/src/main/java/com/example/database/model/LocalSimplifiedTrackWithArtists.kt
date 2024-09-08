package com.example.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.database.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.database.model.entities.LocalSimplifiedArtist
import com.example.database.model.entities.LocalSimplifiedTrack

data class LocalSimplifiedTrackWithArtists(
    @Embedded val simplifiedTrack: LocalSimplifiedTrack,
    @Relation(
        parentColumn = "simplifiedTrackId",
        entityColumn = "simplifiedArtistId",
        associateBy = Junction(SimplifiedTrackArtistCrossRef::class)
    )
    val artists: List<LocalSimplifiedArtist>
)
