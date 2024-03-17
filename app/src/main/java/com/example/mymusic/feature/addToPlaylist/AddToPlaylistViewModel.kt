package com.example.mymusic.feature.addToPlaylist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mymusic.core.designSystem.component.SortOption
import com.example.mymusic.core.model.Playlist
import com.example.mymusic.core.model.Track

class AddToPlaylistViewModel(
    private val trackId: String
): ViewModel() {
    val currentTrack  = Track(
        id = trackId,
        imageUrl = "https://i.scdn.co/image/ab67616d0000b273dd0a40eecd4b13e4c59988da"
    )
    val usersPlaylists: List<Playlist> = listOf(Playlist("", "Dua Lipa", "Polina Polupan"))
    var currentSortOption = mutableStateOf(SortOption.RECENTLY_ADDED)

    fun setSortOption(sortOption: SortOption) {
        currentSortOption.value = sortOption
    }
}
