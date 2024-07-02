package com.example.mymusic.model

data class Track(
    val id: String,
    val album: SimplifiedAlbum,
    val name: String,
    val artists: List<Artist>
)
