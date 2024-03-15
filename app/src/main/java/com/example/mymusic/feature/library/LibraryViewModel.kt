package com.example.mymusic.feature.library

import androidx.compose.runtime.mutableStateOf
import com.example.mymusic.core.designSystem.component.SortOption
import com.example.mymusic.core.model.Playlist

class LibraryViewModel {
    val usersPlaylists: List<Playlist> = listOf(Playlist("", "Dua Lipa", "Polina Polupan"))
    var currentSortOption = mutableStateOf(SortOption.RECENTLY_ADDED)
}