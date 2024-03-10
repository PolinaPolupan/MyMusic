package com.example.mymusic.ui.player

import androidx.lifecycle.ViewModel
import com.example.mymusic.R
import com.example.mymusic.data.Track

class PlayerViewModel: ViewModel() {
    val playingTrack = Track("0", cover = R.drawable.images)
}