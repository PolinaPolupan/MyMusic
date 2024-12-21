package com.example.mymusic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.repository.MusicRepository
import com.example.mymusic.core.data.repository.UserDataRepository
import com.example.mymusic.core.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    musicRepository: MusicRepository
): ViewModel() {

    private val _trackId: StateFlow<String?> = userDataRepository.userPreferencesFlow
        .map { it.trackId }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _trackFlow: Flow<Track?> = _trackId
        .filterNotNull()
        .flatMapLatest { id ->
            musicRepository.observeTrack(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), null)

    private val _isPlaying = userDataRepository.userPreferencesFlow.map { it.isPlaying ?: false }

    val uiState: StateFlow<MainActivityUiState> = combine(_trackFlow, _isPlaying) { track, isPlaying ->
        MainActivityUiState(isPlaying, track)
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MainActivityUiState(false, null))

    fun toggleIsPlaying(isPlaying: Boolean) {
        viewModelScope.launch {
            userDataRepository.setIsPlaying(isPlaying)
        }
    }
}

data class MainActivityUiState(
    val isPlaying: Boolean,
    val currentTrack: Track?
)