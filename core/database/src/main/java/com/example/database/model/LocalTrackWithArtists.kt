package com.example.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.database.model.crossRef.AlbumArtistCrossRef
import com.example.database.model.crossRef.TrackArtistCrossRef
import com.example.database.model.entities.LocalArtist
import com.example.database.model.entities.LocalSimplifiedArtist
import com.example.database.model.entities.LocalTrack
import com.example.database.model.entities.toExternal
import com.example.database.model.entities.toExternalSimplified
import com.example.model.Track

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

@JvmName("LocalTrackWithArtistsToExternalTrack")
fun LocalTrackWithArtists.toExternal() = Track(
    id = track.id,
    album = track.album.toExternalSimplified(albumArtists.toExternal()),
    name = track.name,
    artists = artists.toExternal()
)

@JvmName("LocalTrackWithArtistsListToExternalTracks")
fun List<LocalTrackWithArtists>.toExternal() = map(LocalTrackWithArtists::toExternal)