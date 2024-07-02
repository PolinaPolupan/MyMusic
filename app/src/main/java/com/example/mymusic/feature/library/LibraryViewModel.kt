package com.example.mymusic.feature.library

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mymusic.core.ui.SortOption
import com.example.mymusic.model.Album
import com.example.mymusic.model.Playlist
import com.example.mymusic.core.ui.PreviewParameterData
import com.example.mymusic.model.SimplifiedAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(): ViewModel() {
    val usersPlaylists: List<Playlist> = PreviewParameterData.playlists
    val albums: List<SimplifiedAlbum> = PreviewParameterData.albums
    var currentSortOption = mutableStateOf(SortOption.RECENTLY_ADDED)
}