package com.example.mymusic.feature.home

import androidx.lifecycle.ViewModel
import com.example.mymusic.core.model.Artist
import com.example.mymusic.core.model.Track
import com.example.mymusic.core.ui.PreviewParameterData

class HomeViewModel: ViewModel() {
    val topPicks: List<Track> = PreviewParameterData.tracks
    val moreLikeArtists: Map<Artist, List<Track>> = PreviewParameterData.moreLikeArtists
    val recentlyPlayed: List<Track> = PreviewParameterData.tracks
}