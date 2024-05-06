package com.example.mymusic.feature.player

import androidx.lifecycle.ViewModel
import com.example.mymusic.core.ui.PreviewParameterData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(): ViewModel() {
    val playingTrack = PreviewParameterData.tracks[0]
}