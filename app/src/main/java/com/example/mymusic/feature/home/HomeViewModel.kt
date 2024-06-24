package com.example.mymusic.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.AuthorizationManager
import com.example.mymusic.core.data.UserDataRepository
import com.example.mymusic.core.data.network.MyMusicAPIService
import com.example.mymusic.model.Artist
import com.example.mymusic.model.Track
import com.example.mymusic.core.ui.PreviewParameterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.openid.appauth.AuthState
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicAPIService: MyMusicAPIService,
    userDataRepository: UserDataRepository
): ViewModel()
{
    private val _userDataFlow = userDataRepository.userPreferencesFlow

    val uiState: StateFlow<HomeUiState> =
        _userDataFlow.map {
            HomeUiState.Success(it.imageUrl)
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeUiState.Loading)

    fun getRecommendations() {
        viewModelScope.launch {
            val response = musicAPIService.getRecommendations()
        }
    }
}

sealed interface HomeUiState {
    data object Loading: HomeUiState
    data class Success(
        val userImageUrl: String?,
        val topPicks: List<Track> = PreviewParameterData.tracks,
        val moreLikeArtists: Map<Artist, List<Track>> = PreviewParameterData.moreLikeArtists,
        val recentlyPlayed: List<Track> = PreviewParameterData.tracks
    ): HomeUiState
    data object Error: HomeUiState
}