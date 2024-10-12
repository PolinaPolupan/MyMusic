package com.example.mymusic.core.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.mymusic.core.database.model.entities.LocalAlbum
import com.example.mymusic.core.database.model.entities.LocalSimplifiedArtist
import com.example.mymusic.core.database.model.crossRef.AlbumArtistCrossRef
import com.example.mymusic.core.database.model.entities.toAlbumType
import com.example.mymusic.core.database.model.entities.toExternal
import com.example.mymusic.core.model.SimplifiedAlbum

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

@JvmName("LocalAlbumWithArtistsToExternalSimplifiedAlbum")
fun LocalAlbumWithArtists.toExternalSimplified(): SimplifiedAlbum =
    SimplifiedAlbum(
        id = album.id,
        type = album.type.toAlbumType(),
        imageUrl = album.imageUrl,
        name = album.name,
        artists = simplifiedArtists.map { it.toExternal() }
    )