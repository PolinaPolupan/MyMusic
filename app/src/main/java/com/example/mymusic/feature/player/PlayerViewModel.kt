package com.example.mymusic.feature.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.repository.OfflineFirstMusicRepository
import com.example.mymusic.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val musicRepository: OfflineFirstMusicRepository
): ViewModel() {

    val trackId: String = checkNotNull(savedStateHandle[TRACK_ID_ARG])

    private val _trackFlow: Flow<Track> = musicRepository.observeTrack(trackId)

    val uiState: StateFlow<PlayerUiState> = _trackFlow
        .map { track ->
            PlayerUiState.Success(track = track)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), PlayerUiState.Loading)

    fun loadTrack(id: String) {
        viewModelScope.launch {
            musicRepository.loadTrack(id)
        }
    }

    fun onAlbumClick(id: String) {
        viewModelScope.launch {
            musicRepository.loadAlbumTracks(id)
        }
    }
}

sealed interface PlayerUiState {
    data object Loading: PlayerUiState
    data class Success(
        val track: Track,
    ): PlayerUiState
}