package com.example.mymusic.feature.library

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mymusic.core.data.repository.MusicRepository
import com.example.mymusic.core.data.repository.UserDataRepository
import com.example.mymusic.sync.SyncManager
import com.example.mymusic.core.designsystem.component.SortOption
import com.example.mymusic.feature.home.AuthenticatedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    userDataRepository: UserDataRepository,
    syncManager: SyncManager
): ViewModel() {

    private val _userDataFlow = userDataRepository.userPreferencesFlow

    val authenticatedUiState: StateFlow<AuthenticatedUiState> =
        _userDataFlow
            .map { userData ->
                if (userData.authState.isNullOrEmpty()) AuthenticatedUiState.NotAuthenticated
                else AuthenticatedUiState.Success(userImageUrl = userData.imageUrl)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AuthenticatedUiState.Loading)

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