package com.example.mymusic.core.model

data class Track(
    val id: String,
    val uri: String,
    val album: SimplifiedAlbum,
    val name: String,
    val artists: List<Artist>
)
