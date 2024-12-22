package com.example.mymusic.core.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.mymusic.core.database.model.entities.LocalArtist
import com.example.mymusic.core.database.model.entities.LocalRecentlyPlayed
import com.example.mymusic.core.database.model.entities.LocalSimplifiedArtist
import com.example.mymusic.core.database.model.crossRef.AlbumArtistCrossRef
import com.example.mymusic.core.database.model.crossRef.TrackArtistCrossRef
import com.example.mymusic.core.database.model.entities.toExternal
import com.example.mymusic.core.database.model.entities.toExternalSimplified
import com.example.mymusic.core.model.Track


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

@JvmName("LocalRecentlyPlayedWithArtistsToExternalTrack")
fun LocalRecentlyPlayedWithArtists.toExternal() = Track(
    id = trackHistory.track.id,
    album = trackHistory.track.album.toExternalSimplified(albumArtists.toExternal()),
    name = trackHistory.track.name,
    artists = artists.toExternal(),
    uri = trackHistory.track.uri
)

fun List<LocalRecentlyPlayedWithArtists>.toExternal() = map(LocalRecentlyPlayedWithArtists::toExternal)