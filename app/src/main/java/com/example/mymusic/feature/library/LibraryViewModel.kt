package com.example.mymusic.feature.library

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mymusic.core.data.repository.MusicRepository
import com.example.mymusic.core.data.sync.SyncManager
import com.example.mymusic.core.designSystem.component.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    syncManager: SyncManager
): ViewModel() {

    val savedPlaylists = musicRepository.observeSavedPlaylists()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    val savedAlbums = musicRepository.observeSavedAlbums().cachedIn(viewModelScope)

    val isSyncing = syncManager.isSyncing
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), true)

    val currentSortOption: MutableState<SortOption> = mutableStateOf(SortOption.RECENTLY_ADDED)

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