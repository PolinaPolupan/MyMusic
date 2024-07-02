package com.example.mymusic.core.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.mymusic.model.Track

data class LocalRecentlyPlayedWithArtists(
    @Embedded val trackHistory: LocalRecentlyPlayed,
    @Relation(
        parentColumn = "trackId",
        entityColumn = "artistId",
        associateBy = Junction(TrackArtistCrossRef::class)
    )
    val artists: List<LocalArtist>,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "simplifiedArtistId",
        associateBy = Junction(AlbumArtistCrossRef::class)
    )
    val albumArtists: List<LocalSimplifiedArtist>
)

fun LocalRecentlyPlayedWithArtists.toExternal() = Track(
    id = trackHistory.track.id,
    album = trackHistory.track.album.toExternalSimplified(albumArtists.toExternal()),
    name = trackHistory.track.name,
    artists = artists.toExternal()
)

fun List<LocalRecentlyPlayedWithArtists>.toExternal() = map(LocalRecentlyPlayedWithArtists::toExternal)
