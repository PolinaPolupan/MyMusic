package com.example.mymusic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.UserDataRepository
import com.example.mymusic.core.model.Artist
import com.example.mymusic.core.model.Track
import com.example.mymusic.core.ui.PreviewParameterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userDataRepository: UserDataRepository
): ViewModel()
{
    val authState: StateFlow<String?> = userDataRepository.userPreferencesFlow
        .map {
            it.authState
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), null)

    val topPicks: List<Track> = PreviewParameterData.tracks
    val moreLikeArtists: Map<Artist, List<Track>> = PreviewParameterData.moreLikeArtists
    val recentlyPlayed: List<Track> = PreviewParameterData.tracks
}