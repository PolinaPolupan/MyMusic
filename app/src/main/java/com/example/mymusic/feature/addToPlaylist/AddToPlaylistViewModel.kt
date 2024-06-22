package com.example.mymusic.feature.addToPlaylist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mymusic.core.ui.SortOption
import com.example.mymusic.model.Playlist
import com.example.mymusic.core.ui.PreviewParameterData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddToPlaylistViewModel @Inject constructor(): ViewModel() {
    val currentTrack  = PreviewParameterData.tracks[0]
    val usersPlaylists: List<Playlist> = PreviewParameterData.playlists
    var currentSortOption = mutableStateOf(SortOption.RECENTLY_ADDED)

    fun setSortOption(sortOption: SortOption) {
        currentSortOption.value = sortOption
    }
}
