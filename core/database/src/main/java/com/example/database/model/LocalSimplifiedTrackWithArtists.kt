package com.example.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.database.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.database.model.entities.LocalSimplifiedArtist
import com.example.database.model.entities.LocalSimplifiedTrack
import com.example.database.model.entities.toExternal
import com.example.model.SimplifiedTrack

data class LocalSimplifiedTrackWithArtists(
    @Embedded val simplifiedTrack: LocalSimplifiedTrack,
    @Relation(
        parentColumn = "simplifiedTrackId",
        entityColumn = "simplifiedArtistId",
        associateBy = Junction(SimplifiedTrackArtistCrossRef::class)
    )
    val artists: List<LocalSimplifiedArtist>
)

@JvmName("LocalSimplifiedTrackWithArtistsToExternalSimplifiedTrack")
fun LocalSimplifiedTrackWithArtists.toExternal() = SimplifiedTrack(
    id = simplifiedTrack.id,
    name = simplifiedTrack.name,
    artists = artists.toExternal()
)

@JvmName("LocalSimplifiedTrackWithArtistsListToExternalSimplifiedTracks")
fun List<LocalSimplifiedTrackWithArtists>.toExternal() = map(LocalSimplifiedTrackWithArtists::toExternal)