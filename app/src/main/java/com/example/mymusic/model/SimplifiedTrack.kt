package com.example.mymusic.model

data class SimplifiedTrack(
    val id: String,
    val name: String,
    val artists: List<SimplifiedArtist>
)
