package com.example.mymusic.data

import androidx.annotation.DrawableRes

data class Playlist(
    @DrawableRes val coverRes: Int,
    val name: String,
    val owner: String
)
