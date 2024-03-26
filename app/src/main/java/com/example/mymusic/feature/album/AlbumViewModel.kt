package com.example.mymusic.feature.album

import androidx.lifecycle.ViewModel
import com.example.mymusic.core.model.Album
import com.example.mymusic.core.ui.PreviewParameterData

class AlbumViewModel : ViewModel() {
    val currentAlbum: Album = PreviewParameterData.albums[0]
}