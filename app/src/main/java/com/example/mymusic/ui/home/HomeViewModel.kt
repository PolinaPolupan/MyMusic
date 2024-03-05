package com.example.mymusic.ui.home

import androidx.lifecycle.ViewModel
import com.example.mymusic.R
import com.example.mymusic.data.TempArtist
import com.example.mymusic.data.Track

class HomeViewModel: ViewModel() {
    val topPicks: List<Track> = listOf(
        Track(R.drawable.dua_lipa___future_nostalgia__official_album_cover_),
        Track(R.drawable.images),
        Track(R.drawable.screenshot_2024_02_18_at_15_27_40_todays_top_hits)
    )
    val artists: List<TempArtist> = listOf(
        TempArtist(
            name = "Dua Lipa",
            picture = R.drawable.dua_lipa___future_nostalgia__official_album_cover_,
            topTracks = listOf(
                Track(R.drawable.dua_lipa___future_nostalgia__official_album_cover_),
                Track(R.drawable.images),
                Track(R.drawable.screenshot_2024_02_18_at_15_27_40_todays_top_hits)
            )
        ),
        TempArtist(
            name = "Dua Lipa",
            picture = R.drawable.dua_lipa___future_nostalgia__official_album_cover_,
            topTracks = listOf(
                Track(R.drawable.dua_lipa___future_nostalgia__official_album_cover_),
                Track(R.drawable.images),
                Track(R.drawable.screenshot_2024_02_18_at_15_27_40_todays_top_hits)
            )
        ),
    )
    val recentlyPlayed: List<Track> = listOf(
        Track(R.drawable.dua_lipa___future_nostalgia__official_album_cover_),
        Track(R.drawable.images),
        Track(R.drawable.screenshot_2024_02_18_at_15_27_40_todays_top_hits)
    )
}