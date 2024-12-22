package com.example.mymusic.feature.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.appremote.SpotifyAppRemoteManager
import com.example.mymusic.core.data.repository.MusicRepository
import com.example.mymusic.core.data.repository.UserDataRepository
import com.example.mymusic.core.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userDataRepository: UserDataRepository,
    private val musicRepository: MusicRepository,
    private val appRemoteManager: SpotifyAppRemoteManager
): ViewModel() {

    val trackId: String = checkNotNull(savedStateHandle[TRACK_ID_ARG])

    private val _trackFlow: Flow<Track> = musicRepository.observeTrack(trackId)

    private var _isPlaying = userDataRepository.userPreferencesFlow.map { it.isPlaying ?: false }

    val uiState: StateFlow<PlayerUiState> = combine(_trackFlow, _isPlaying) { track, isPlaying ->
        PlayerUiState.Success(track = track, isPlaying = isPlaying)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), PlayerUiState.Loading)

    fun onAlbumClick(id: String) {
        viewModelScope.launch {
            musicRepository.loadAlbumTracks(id)
        }
    }

    fun toggleIsPlaying(isPlaying: Boolean) {
        viewModelScope.launch {
            userDataRepository.setIsPlaying(isPlaying)

            if (isPlaying) {
                appRemoteManager.play(_trackFlow.first().uri);
            } else {
                appRemoteManager.pause();
            }
        }
    }
}

sealed interface PlayerUiState {
    data object Loading: PlayerUiState
    data class Success(
        val track: Track,
        val isPlaying: Boolean
    ): PlayerUiState
}