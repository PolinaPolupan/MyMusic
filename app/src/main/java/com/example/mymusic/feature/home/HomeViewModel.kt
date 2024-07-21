package com.example.mymusic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.sync.SyncManager
import com.example.mymusic.core.data.repository.MusicRepository
import com.example.mymusic.core.data.repository.UserDataRepository
import com.example.mymusic.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
    musicRepository: MusicRepository,
    syncManager: SyncManager
): ViewModel()
{
    private val _userDataFlow = userDataRepository.userPreferencesFlow
    private val _recommendationsFlow = musicRepository.observeRecommendations()
    private val _recentlyPlayedFlow = musicRepository.observeRecentlyPlayed()

    val authenticatedUiState: StateFlow<AuthenticatedUiState> =
        _userDataFlow
            .map { userData ->
            if (userData.authState.isNullOrEmpty()) AuthenticatedUiState.NotAuthenticated
            else AuthenticatedUiState.Success(userImageUrl = userData.imageUrl)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AuthenticatedUiState.Loading)

    val isSyncing = syncManager.isSyncing
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), true)

    val uiState: StateFlow<HomeUiState> =
        combine(
            _recommendationsFlow,
            _recentlyPlayedFlow
        ) {
            recommendations, recentlyPlayed ->
            when (recentlyPlayed.isEmpty() || recommendations.isEmpty()) {
                true -> HomeUiState.Loading
                false -> HomeUiState.Success(
                    topPicks = recommendations,
                    recentlyPlayed = recentlyPlayed
                )
            }
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeUiState.Loading)
}

sealed interface HomeUiState {

    data object Loading: HomeUiState

    data class Success(
        val topPicks: List<Track>,
        val recentlyPlayed: List<Track>,
    ): HomeUiState
}

sealed interface AuthenticatedUiState {

    data object Loading: AuthenticatedUiState

    data class Success(
        val userImageUrl: String?
    ): AuthenticatedUiState

    data object NotAuthenticated: AuthenticatedUiState
}