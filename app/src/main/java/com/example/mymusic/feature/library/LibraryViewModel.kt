package com.example.mymusic.feature.library

import androidx.compose.runtime.mutableStateOf
import com.example.mymusic.core.designSystem.component.SortOption
import com.example.mymusic.core.model.Album
import com.example.mymusic.core.model.Playlist
import com.example.mymusic.core.ui.PreviewParameterData

class LibraryViewModel {
    val usersPlaylists: List<Playlist> = PreviewParameterData.playlists
    val albums: List<Album> = PreviewParameterData.albums
    var currentSortOption = mutableStateOf(SortOption.RECENTLY_ADDED)
}