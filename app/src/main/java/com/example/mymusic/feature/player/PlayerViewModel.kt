package com.example.mymusic.feature.player

import androidx.lifecycle.ViewModel
import com.example.mymusic.core.model.Track

class PlayerViewModel: ViewModel() {
    val playingTrack = Track("0", coverUrl = "")
}