package com.example.mymusic.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "artists")
data class LocalArtist(
    @ColumnInfo(name = "artistId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "artistName") val name: String,
    @ColumnInfo(name = "artistImageUrl") val imageUrl: String?
)

@Entity(
    tableName = "simplifiedArtists",
    foreignKeys = [ForeignKey(
        entity = LocalArtist::class,
        parentColumns = arrayOf("artistId"),
        childColumns = arrayOf("simplifiedArtistId"),
        onUpdate = ForeignKey.CASCADE
    )]
)
data class LocalSimplifiedArtist(
    @ColumnInfo(name = "simplifiedArtistId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "simplifiedArtistName") val name: String
)

@Entity(tableName = "albums")
data class LocalAlbum(
    @ColumnInfo(name = "albumId")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "albumType") val type: String,
    @ColumnInfo(name = "albumImageUrl") val imageUrl: String,
    @ColumnInfo(name = "albumName") val name: String,
)

@Entity(
    tableName = "tracks",
    foreignKeys = [ForeignKey(
        entity = LocalAlbum::class,
        parentColumns = arrayOf("albumId"),
        childColumns = arrayOf("trackId"),
        onUpdate = ForeignKey.CASCADE
    )]
)
data class LocalTrack(
    @ColumnInfo(name = "trackId")
    @PrimaryKey val id: String,
    @Embedded val album: LocalAlbum,
    @ColumnInfo(name = "trackName") val name: String
)

@Entity(primaryKeys = ["simplifiedArtistId", "albumId"])
data class AlbumArtistCrossRef(
    val simplifiedArtistId: String,
    val albumId: String
)

data class LocalAlbumWithArtists(
    @Embedded val album: LocalAlbum,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "simplifiedArtistId",
        associateBy = Junction(AlbumArtistCrossRef::class)
    )
    val simplifiedArtists: List<LocalSimplifiedArtist>
)

@Entity(primaryKeys = ["artistId", "trackId"])
data class TrackArtistCrossRef(
    val artistId: String,
    val trackId: String
)

data class LocalTrackWithArtists(
    @Embedded val track: LocalTrack,
    @Relation(
        parentColumn = "trackId",
        entityColumn = "artistId",
        associateBy = Junction(TrackArtistCrossRef::class)
    )
    val artists: List<LocalArtist>
)