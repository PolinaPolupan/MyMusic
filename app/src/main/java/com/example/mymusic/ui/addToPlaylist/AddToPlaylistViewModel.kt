package com.example.mymusic.ui.addToPlaylist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mymusic.R
import com.example.mymusic.data.Playlist
import com.example.mymusic.designSystem.component.SortOption

class AddToPlaylistViewModel: ViewModel() {
    val usersPlaylists: List<Playlist> = listOf(Playlist(R.drawable.images, "Dua Lipa", "Polina Polupan"))
    var currentSortOption = mutableStateOf(SortOption.RECENTLY_ADDED)

    fun setSortOption(sortOption: SortOption) {
        currentSortOption.value = sortOption
    }
}
