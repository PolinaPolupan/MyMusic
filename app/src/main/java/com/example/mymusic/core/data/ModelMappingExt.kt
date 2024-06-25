package com.example.mymusic.core.data

import com.example.mymusic.core.data.network.SpotifyAlbum
import com.example.mymusic.core.data.network.SpotifyArtist
import com.example.mymusic.core.data.network.SpotifySimplifiedArtist
import com.example.mymusic.core.data.network.SpotifyTrack
import com.example.mymusic.model.Album
import com.example.mymusic.model.AlbumType
import com.example.mymusic.model.Artist
import com.example.mymusic.model.SimplifiedArtist
import com.example.mymusic.model.Track

fun String.toAlbumType(): AlbumType {
    return when(this) {
        "album" -> AlbumType.Album
        "single" -> AlbumType.Single
        "compilation" -> AlbumType.Compilation
        else -> {
            throw IllegalArgumentException("Unknown type")
        }
    }
}

fun SpotifySimplifiedArtist.toExternal() = SimplifiedArtist(
    id = id,
    name = name
)

fun SpotifyAlbum.toExternal() = Album(
    id = id,
    type = type.toAlbumType(),
    imageUrl = if (images.isNotEmpty()) images[0].url else "",
    name = name,
    artists = artists.map { it.toExternal() }
)

fun SpotifyArtist.toExternal() = Artist(
    id = id,
    name = name,
    imageUrl = if (images.isNotEmpty()) images[0].url else ""
)

fun SpotifyTrack.toExternal() = Track(
    id = id,
    album = album.toExternal(),
    name = name,
    artists = artists.map { it.toExternal() }
)