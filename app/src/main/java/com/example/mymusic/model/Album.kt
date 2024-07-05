package com.example.mymusic.model

enum class AlbumType {
    Album,
    Single,
    Compilation
}

/**
 * [Album] defines an album, which holds tracks.
 * [Album] and [SimplifiedAlbum] were divided in order to fit the UI data
 * (some screens don't need to have any information about tracks of albums)
 * and Spotify API response
 * https://developer.spotify.com/documentation/web-api/reference/get-an-album
 */
data class Album(
    val id: String,
    val type: AlbumType,
    val imageUrl: String,
    val name: String,
    val artists: List<SimplifiedArtist>,
    val tracks: List<SimplifiedTrack>
)
