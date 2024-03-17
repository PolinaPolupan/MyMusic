package com.example.mymusic.feature.player

import androidx.lifecycle.ViewModel
import com.example.mymusic.core.model.Track

class PlayerViewModel: ViewModel() {
    val playingTrack = Track(
        id = "3",
        imageUrl = "https://i.scdn.co/image/ab67616d0000b27352b2a3824413eefe9e33817a",
        name = "Blank Space",
        artist = "Taylor Swift"
    )
}