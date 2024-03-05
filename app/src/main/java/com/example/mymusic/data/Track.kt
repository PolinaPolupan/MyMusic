package com.example.mymusic.data

import androidx.annotation.DrawableRes

data class Track(
    @DrawableRes val cover: Int,
    val name: String = "Name",
    val artist: String = "Artist"
)
