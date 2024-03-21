package com.example.mymusic.feature.player

import androidx.lifecycle.ViewModel
import com.example.mymusic.core.model.Track
import com.example.mymusic.core.ui.PreviewParameterData

class PlayerViewModel: ViewModel() {
    val playingTrack = PreviewParameterData.tracks[0]
}