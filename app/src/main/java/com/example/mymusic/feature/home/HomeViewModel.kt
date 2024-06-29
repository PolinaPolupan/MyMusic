package com.example.mymusic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.MusicRepository
import com.example.mymusic.core.data.UserDataRepository
import com.example.mymusic.model.Artist
import com.example.mymusic.model.Track
import com.example.mymusic.core.ui.PreviewParameterData
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
    private val _recommendationsFlow = musicRepository.getRecommendationsStream()

    val uiState: StateFlow<HomeUiState> =
        combine(
           _userDataFlow,
            _recommendationsFlow
        ) {
            userData, recommendations ->
            when (recommendations.isEmpty() || userData.authState.isNullOrBlank()) {
                true -> HomeUiState.Loading
                false ->  HomeUiState.Success(
                    userImageUrl = userData.imageUrl,
                    topPicks = recommendations
                )
            }
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeUiState.Loading)
}

sealed interface HomeUiState {
    data object Loading: HomeUiState
    data class Success(
        val userImageUrl: String?,
        val topPicks: List<Track>,
        val moreLikeArtists: Map<Artist, List<Track>> = PreviewParameterData.moreLikeArtists,
        val recentlyPlayed: List<Track> = PreviewParameterData.tracks
    ): HomeUiState
    data object Error: HomeUiState
}