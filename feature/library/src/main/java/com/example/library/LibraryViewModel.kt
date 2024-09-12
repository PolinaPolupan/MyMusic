package com.example.library

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.designsystem.component.SortOption
import com.example.sync.SyncManager
import com.example.home.AuthenticatedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val musicRepository: com.example.data.repository.OfflineFirstMusicRepository,
    userDataRepository: com.example.data.repository.OfflineFirstUserDataRepository,
    syncManager: SyncManager
): ViewModel() {

    private val _userDataFlow = userDataRepository.userPreferencesFlow

    val authenticatedUiState: StateFlow<com.example.home.AuthenticatedUiState> =
        _userDataFlow
            .map { userData ->
                if (userData.authState.isNullOrEmpty()) com.example.home.AuthenticatedUiState.NotAuthenticated
                else com.example.home.AuthenticatedUiState.Success(userImageUrl = userData.imageUrl)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), com.example.home.AuthenticatedUiState.Loading)

    val savedPlaylists = musicRepository.observeSavedPlaylists().cachedIn(viewModelScope)

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