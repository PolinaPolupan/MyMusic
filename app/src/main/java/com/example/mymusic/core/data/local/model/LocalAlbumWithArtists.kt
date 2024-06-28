package com.example.mymusic.core.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.mymusic.core.data.network.model.toAlbumType
import com.example.mymusic.model.Album

/**
 * [LocalAlbumWithArtists] defines an album with the list of artists.
 */
data class LocalAlbumWithArtists(
    @Embedded val album: LocalAlbum,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "simplifiedArtistId",
        associateBy = Junction(AlbumArtistCrossRef::class)
    )
    val simplifiedArtists: List<LocalSimplifiedArtist>
)

fun LocalAlbumWithArtists.toExternal() = Album(
    id = album.id,
    type = album.type.toAlbumType(),
    imageUrl = album.imageUrl,
    name = album.name,
    artists = simplifiedArtists.map { it.toExternal() }
)