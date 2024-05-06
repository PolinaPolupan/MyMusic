package com.example.mymusic.feature.album

import androidx.lifecycle.ViewModel
import com.example.mymusic.core.model.Album
import com.example.mymusic.core.ui.PreviewParameterData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor() : ViewModel() {
    val currentAlbum: Album = PreviewParameterData.albums[0]
}