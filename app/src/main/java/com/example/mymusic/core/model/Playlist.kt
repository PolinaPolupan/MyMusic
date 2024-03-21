package com.example.mymusic.core.model

data class Playlist(
    val id: String,
    val imageUrl: String,
    val name: String,
    val owner: String,
    val tracks: List<Track>
)
