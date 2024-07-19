package com.example.mymusic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.MusicRepository
import com.example.mymusic.core.data.UserDataRepository
import com.example.mymusic.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
    musicRepository: MusicRepository
): ViewModel()
{
    private val _userDataFlow = userDataRepository.userPreferencesFlow
    private val _recommendationsFlow = musicRepository.observeRecommendations()
    private val _recentlyPlayedFlow = musicRepository.observeRecentlyPlayed()

    val uiState: StateFlow<HomeUiState> =
        combine(
           _userDataFlow,
            _recommendationsFlow,
            _recentlyPlayedFlow
        ) {
            userData, recommendations, recentlyPlayed ->
            if (userData.authState == null) HomeUiState.Error
            else {
                when (recentlyPlayed.isEmpty() || recommendations.isEmpty()) {
                    true -> HomeUiState.Loading
                    false -> HomeUiState.Success(
                        userImageUrl = userData.imageUrl,
                        topPicks = recommendations,
                        recentlyPlayed = recentlyPlayed
                    )
                }
            }
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeUiState.Loading)
}

sealed interface HomeUiState {
    data object Loading: HomeUiState
    data class Success(
        val userImageUrl: String?,
        val topPicks: List<Track>,
        val recentlyPlayed: List<Track>,
    ): HomeUiState
    data object Error: HomeUiState
}