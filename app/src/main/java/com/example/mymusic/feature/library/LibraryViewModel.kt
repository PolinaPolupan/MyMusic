package com.example.mymusic.feature.library

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.repository.MusicRepository
import com.example.mymusic.core.ui.SortOption
import com.example.mymusic.core.ui.PreviewParameterData
import com.example.mymusic.model.SimplifiedAlbum
import com.example.mymusic.model.SimplifiedPlaylist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val musicRepository: MusicRepository
): ViewModel() {

    private val _savedAlbumsFlow = musicRepository.observeSavedAlbums()

    private val _savedPlaylistsFlow = musicRepository.observeSavedPlaylists()

    val uiState: StateFlow<LibraryUiState> = combine(
        _savedAlbumsFlow,
        _savedPlaylistsFlow
    ) { albums, playlists ->
        LibraryUiState.Success(
            savedAlbums = albums,
            savedPlaylists = playlists
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LibraryUiState.Loading)

    val albums: List<SimplifiedAlbum> = PreviewParameterData.albums

    fun onAlbumClick(id: String) {
        viewModelScope.launch {
            musicRepository.loadAlbumTracks(id)
        }
    }

    fun onPlaylistClick(id: String) {
        viewModelScope.launch {
            musicRepository.loadPlaylistTracks(id)
        }
    }
}

sealed interface LibraryUiState {
    data object Loading: LibraryUiState
    data class Success(
        val savedAlbums: List<SimplifiedAlbum>,
        val savedPlaylists: List<SimplifiedPlaylist> = PreviewParameterData.simplifiedPlaylists,
        val currentSortOption: MutableState<SortOption> = mutableStateOf(SortOption.RECENTLY_ADDED)
    ): LibraryUiState
}