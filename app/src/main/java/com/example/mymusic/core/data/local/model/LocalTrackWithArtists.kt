package com.example.mymusic.core.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.mymusic.model.Track

data class LocalTrackWithArtists(
    @Embedded val track: LocalTrack,
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

fun LocalTrackWithArtists.toExternal() = Track(
    id = track.id,
    album = track.album.toExternal(albumArtists.map { it.toExternal() }),
    name = track.name,
    artists = artists.map { it.toExternal() }
)

fun List<LocalTrackWithArtists>.toExternal() = map(LocalTrackWithArtists::toExternal)