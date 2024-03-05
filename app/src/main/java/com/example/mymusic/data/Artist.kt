package com.example.mymusic.data

import androidx.annotation.DrawableRes

data class TempArtist(
    val name: String,
    @DrawableRes val picture: Int,
    val topTracks: List<Track>
)
