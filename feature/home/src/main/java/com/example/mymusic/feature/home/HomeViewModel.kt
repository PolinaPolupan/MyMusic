package com.example.mymusic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mymusic.appremote.SpotifyAppRemoteManager
import com.example.mymusic.core.data.repository.MusicRepository
import com.example.mymusic.core.data.repository.UserDataRepository
import com.example.mymusic.core.model.Track
import com.example.mymusic.sync.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val appRemoteManager: SpotifyAppRemoteManager,
    musicRepository: MusicRepository,
    syncManager: SyncManager
): ViewModel()
{
    private val _userDataFlow = userDataRepository.userPreferencesFlow
    private val _recommendationsFlow = musicRepository.observeRecommendations()

    val authenticatedUiState: StateFlow<AuthenticatedUiState> =
        _userDataFlow
            .map { userData ->
            if (userData.authState.isNullOrEmpty()) AuthenticatedUiState.NotAuthenticated
            else AuthenticatedUiState.Success(userImageUrl = userData.imageUrl)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L),
            AuthenticatedUiState.Loading
        )

    val isSyncing = syncManager.isSyncing
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), true)

    val uiState: StateFlow<HomeUiState> = _recommendationsFlow
        .map { recommendations-> HomeUiState.Success(topPicks = recommendations) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeUiState.Loading)

    val recentlyPlayed = musicRepository.observeRecentlyPlayed().cachedIn(viewModelScope)

    fun onTrackClick(isPlaying: Boolean, track: Track) {
        viewModelScope.launch {
            userDataRepository.setIsPlaying(isPlaying)
            userDataRepository.setTrackId(track.id)
        }
        appRemoteManager.play(track.uri)
    }
}

sealed interface HomeUiState {

    data object Loading: HomeUiState

    data class Success(
        val topPicks: List<Track>
    ): HomeUiState
}

sealed interface AuthenticatedUiState {

    data object Loading: AuthenticatedUiState

    data class Success(
        val userImageUrl: String?
    ): AuthenticatedUiState

    data object NotAuthenticated: AuthenticatedUiState
}