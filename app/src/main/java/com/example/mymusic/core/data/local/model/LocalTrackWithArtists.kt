package com.example.mymusic.core.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class LocalTrackWithArtists(
    @Embedded val track: LocalTrack,
    @Relation(
        parentColumn = "trackId",
        entityColumn = "artistId",
        associateBy = Junction(TrackArtistCrossRef::class)
    )
    val artists: List<LocalArtist>
)