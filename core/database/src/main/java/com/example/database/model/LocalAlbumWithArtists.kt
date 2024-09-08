package com.example.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.database.model.crossRef.AlbumArtistCrossRef
import com.example.database.model.entities.LocalAlbum
import com.example.database.model.entities.LocalSimplifiedArtist

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
