package com.example.mymusic.feature.library

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mymusic.core.ui.SortOption
import com.example.mymusic.core.model.Album
import com.example.mymusic.core.model.Playlist
import com.example.mymusic.core.ui.PreviewParameterData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(): ViewModel() {
    val usersPlaylists: List<Playlist> = PreviewParameterData.playlists
    val albums: List<Album> = PreviewParameterData.albums
    var currentSortOption = mutableStateOf(SortOption.RECENTLY_ADDED)
}