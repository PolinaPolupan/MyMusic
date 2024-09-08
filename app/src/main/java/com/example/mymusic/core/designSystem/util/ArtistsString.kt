package com.example.mymusic.core.designSystem.util

import com.example.model.Artist
import com.example.model.SimplifiedArtist

@JvmName("artistsListMethod")
fun artistsString(artists: List<com.example.model.SimplifiedArtist>) : String {
    var artistsString = ""
    for (artist in artists) {
        if (artist.name.isNotBlank()) artistsString += artist.name + ", "
    }
    if (artistsString.length > 2) {
        artistsString = artistsString.substring(0, artistsString.length - 2)
    }
    return artistsString
}

@JvmName("simplifiedArtistsListMethod")
fun artistsString(artists: List<com.example.model.Artist>) : String {
    var artistsString = ""
    for (artist in artists) {
        if (artist.name.isNotBlank()) artistsString += artist.name + ", "
    }
    if (artistsString.length > 2) {
        artistsString = artistsString.substring(0, artistsString.length - 2)
    }
    return artistsString
}