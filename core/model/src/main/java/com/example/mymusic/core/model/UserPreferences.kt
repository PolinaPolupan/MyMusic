package com.example.mymusic.core.model

data class UserPreferences(
    val authState: String?,
    val displayName: String?,
    val email: String?,
    val imageUrl: String?,
    val trackId: String?,
    val isPlaying: Boolean?
)
