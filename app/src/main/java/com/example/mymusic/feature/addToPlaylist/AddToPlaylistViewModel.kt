package com.example.mymusic.feature.addToPlaylist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mymusic.core.designSystem.component.SortOption
import com.example.mymusic.core.model.Playlist
import com.example.mymusic.core.model.Track
import com.example.mymusic.core.ui.PreviewParameterData

class AddToPlaylistViewModel(
    private val trackId: String
): ViewModel() {
    val currentTrack  = PreviewParameterData.tracks[0]
    val usersPlaylists: List<Playlist> = listOf(Playlist("", "Dua Lipa", "Polina Polupan"))
    var currentSortOption = mutableStateOf(SortOption.RECENTLY_ADDED)

    fun setSortOption(sortOption: SortOption) {
        currentSortOption.value = sortOption
    }
}
