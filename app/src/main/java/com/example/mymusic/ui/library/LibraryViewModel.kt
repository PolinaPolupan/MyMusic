package com.example.mymusic.ui.library

import androidx.compose.runtime.mutableStateOf
import com.example.mymusic.R
import com.example.mymusic.data.Playlist
import com.example.mymusic.designSystem.component.SortOption

class LibraryViewModel {
    val usersPlaylists: List<Playlist> = listOf(Playlist(R.drawable.images, "Dua Lipa", "Polina Polupan"))
    var currentSortOption = mutableStateOf(SortOption.RECENTLY_ADDED)
    var showBottomSheet = mutableStateOf(false)
}