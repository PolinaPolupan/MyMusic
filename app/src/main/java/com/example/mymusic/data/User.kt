package com.example.mymusic.data

import androidx.annotation.DrawableRes

data class User(
    val id: String,
    val name: String,
    @DrawableRes val coverRes: Int
)
