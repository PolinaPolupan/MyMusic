package com.example.designsystem.util

import com.example.model.Artist
import com.example.model.SimplifiedArtist

@JvmName("artistsListMethod")
fun artistsString(artists: List<SimplifiedArtist>) : String {
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
fun artistsString(artists: List<Artist>) : String {
    var artistsString = ""
    for (artist in artists) {
        if (artist.name.isNotBlank()) artistsString += artist.name + ", "
    }
    if (artistsString.length > 2) {
        artistsString = artistsString.substring(0, artistsString.length - 2)
    }
    return artistsString
}