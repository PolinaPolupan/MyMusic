package com.example.mymusic.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.mymusic.core.model.Track

class TracksPreviewParameterProvider: PreviewParameterProvider<List<Track>> {
    override val values: Sequence<List<Track>>
        get() = sequenceOf(
            PreviewParameterData.tracks
        )
}
