package com.example.addtoplaylist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.repository.OfflineFirstMusicRepository
import com.example.designsystem.component.SortOption
import com.example.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AddToPlaylistViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    musicRepository: com.example.data.repository.OfflineFirstMusicRepository
): ViewModel() {

    private val _trackId: String = checkNotNull(savedStateHandle[TRACK_ID_ARG])

    private val _trackFlow: Flow<com.example.model.Track?> = musicRepository.observeTrack(_trackId)

    val savedPlaylists = musicRepository.observeSavedPlaylists().cachedIn(viewModelScope)

    val uiState: StateFlow<AddToPlaylistUiState> =
        _trackFlow.map { track ->
        if (track != null) {
            AddToPlaylistUiState.Success(
                track = track
            )
        } else {
            AddToPlaylistUiState.Loading
        }
    }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L),
                AddToPlaylistUiState.Loading
            )

    var currentSortOption = mutableStateOf(SortOption.RECENTLY_ADDED)

    fun setSortOption(sortOption: SortOption) {
        currentSortOption.value = sortOption
    }
}

sealed interface AddToPlaylistUiState {
    data object Loading: AddToPlaylistUiState
    data class Success(
        val track: com.example.model.Track
    ): AddToPlaylistUiState
}