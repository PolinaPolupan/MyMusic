package com.example.mymusic.core.ui

import com.example.mymusic.core.model.Artist
import com.example.mymusic.core.model.SimplifiedArtist

@JvmName("artistsListMethod")
fun artistsString(artists: List<SimplifiedArtist>) : String {
    var artistsString = ""
    for (artist in artists) {
        artistsString += artist.name + ", "
    }
    artistsString = artistsString.substring(0, artistsString.length - 2)
    return artistsString
}

@JvmName("simplifiedArtistsListMethod")
fun artistsString(artists: List<Artist>) : String {
    var artistsString = ""
    for (artist in artists) {
        artistsString += artist.name + ", "
    }
    artistsString = artistsString.substring(0, artistsString.length - 2)
    return artistsString
}