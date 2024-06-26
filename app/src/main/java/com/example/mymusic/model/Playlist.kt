package com.example.mymusic.model

data class Playlist(
    val id: String,
    val imageUrl: String,
    val name: String,
    val ownerName: String,
    val tracks: List<Track>
)
