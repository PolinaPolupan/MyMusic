package com.example.mymusic.feature.playlist

import androidx.lifecycle.ViewModel
import com.example.mymusic.core.ui.PreviewParameterData

class PlaylistViewModel : ViewModel() {
    val playlist = PreviewParameterData.playlists[0]
}