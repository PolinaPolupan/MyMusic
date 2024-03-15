package com.example.mymusic.feature.addToPlaylist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mymusic.core.designSystem.component.SortOption
import com.example.mymusic.core.model.Playlist

class AddToPlaylistViewModel: ViewModel() {
    val usersPlaylists: List<Playlist> = listOf(Playlist("", "Dua Lipa", "Polina Polupan"))
    var currentSortOption = mutableStateOf(SortOption.RECENTLY_ADDED)

    fun setSortOption(sortOption: SortOption) {
        currentSortOption.value = sortOption
    }
}
