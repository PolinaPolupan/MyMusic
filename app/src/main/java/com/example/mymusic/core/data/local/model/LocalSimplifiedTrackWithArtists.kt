package com.example.mymusic.core.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.mymusic.model.SimplifiedTrack

data class LocalSimplifiedTrackWithArtists(
    @Embedded val simplifiedTrack: LocalSimplifiedTrack,
    @Relation(
        parentColumn = "simplifiedTrackId",
        entityColumn = "simplifiedArtistId",
        associateBy = Junction(SimplifiedTrackArtistCrossRef::class)
    )
    val artists: List<LocalSimplifiedArtist>
)

fun LocalSimplifiedTrackWithArtists.toExternal() = SimplifiedTrack(
    id = simplifiedTrack.id,
    name = simplifiedTrack.name,
    artists = artists.toExternal()
)

fun List<LocalSimplifiedTrackWithArtists>.toExternal() = map(LocalSimplifiedTrackWithArtists::toExternal)