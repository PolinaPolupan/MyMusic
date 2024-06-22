package com.example.mymusic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.UserDataRepository
import com.example.mymusic.model.Artist
import com.example.mymusic.model.Track
import com.example.mymusic.core.ui.PreviewParameterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.openid.appauth.AuthState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userDataRepository: UserDataRepository
): ViewModel()
{
    private val _userDataFlow = userDataRepository.userPreferencesFlow

    val uiState: StateFlow<HomeUiState> =
        _userDataFlow.map {
            if (it.authState != null) HomeUiState.Success(it.authState)
            else HomeUiState.Error
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeUiState.Loading)
}

sealed interface HomeUiState {
    data object Loading: HomeUiState
    data class Success(
        val authState: String,
        val topPicks: List<Track> = PreviewParameterData.tracks,
        val moreLikeArtists: Map<Artist, List<Track>> = PreviewParameterData.moreLikeArtists,
        val recentlyPlayed: List<Track> = PreviewParameterData.tracks
    ): HomeUiState
    data object Error: HomeUiState
}