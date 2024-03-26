package com.example.mymusic.core.model

data class Track(
    val id: String,
    val album: Album,
    val imageUrl: String,
    val name: String,
    val artists: List<Artist>
)
