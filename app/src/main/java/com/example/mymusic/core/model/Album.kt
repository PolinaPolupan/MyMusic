package com.example.mymusic.core.model

data class Album(
    val id: String,
    val imageUrl: String,
    val name: String,
    val artists: List<Artist>,
    val tracks: List<Track>
)
