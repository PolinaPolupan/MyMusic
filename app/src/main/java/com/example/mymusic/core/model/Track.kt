package com.example.mymusic.core.model

data class Track(
    val id: String,
    val coverUrl: String,
    val name: String = "Name",
    val artist: String = "Artist"
)
