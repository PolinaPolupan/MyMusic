package com.example.mymusic.model

import com.example.mymusic.R

enum class AlbumType(typeName: Int) {
    Album(R.string.album_type_name),
    Single(R.string.single_type_name),
    Compilation(R.string.compilation_type_name)
}

data class Album(
    val id: String,
    val type: AlbumType,
    val imageUrl: String,
    val name: String,
    val artists: List<SimplifiedArtist>
)
