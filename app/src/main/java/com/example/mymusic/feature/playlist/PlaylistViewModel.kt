package com.example.mymusic.feature.playlist

import androidx.lifecycle.ViewModel
import com.example.mymusic.core.ui.PreviewParameterData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(): ViewModel() {
    val playlist = PreviewParameterData.playlists[0]
}