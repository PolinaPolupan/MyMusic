package com.example.mymusic.core.data

import com.example.mymusic.core.data.local.LocalAlbum
import com.example.mymusic.core.data.local.LocalArtist
import com.example.mymusic.core.data.local.LocalSimplifiedArtist
import com.example.mymusic.core.data.local.LocalTrack
import com.example.mymusic.core.data.network.RecommendationsResponse
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
    imageUrl = if (images?.isNotEmpty() == true) images[0].url else ""
)

fun SpotifyTrack.toExternal() = Track(
    id = id,
    album = album.toExternal(),
    name = name,
    artists = artists.map { it.toExternal() }
)

fun RecommendationsResponse.toExternal(): List<Track> = this.tracks.map { it.toExternal() }

fun LocalArtist.toExternal() = Artist(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun LocalSimplifiedArtist.toExternal() = SimplifiedArtist(
    id = id,
    name = name
)

fun LocalAlbum.toExternal(artists: List<SimplifiedArtist>) = Album(
    id = id,
    type = type.toAlbumType(),
    imageUrl = imageUrl,
    name = name,
    artists = artists
)

fun LocalTrack.toExternal(albumArtists: List<SimplifiedArtist>, artists: List<Artist>) = Track(
    id = id,
    album = album.toExternal(albumArtists),
    name = name,
    artists = artists
)