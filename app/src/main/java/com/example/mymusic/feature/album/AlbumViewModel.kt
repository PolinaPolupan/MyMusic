package com.example.mymusic.feature.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.MusicRepository
import com.example.mymusic.model.Album
import com.example.mymusic.model.SimplifiedAlbum
import com.example.mymusic.model.SimplifiedTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    musicRepository: MusicRepository
) : ViewModel() {

    private val _albumId: String = checkNotNull(savedStateHandle[ALBUM_ID_ARG])

    private val _albumFlow: Flow<SimplifiedAlbum> = musicRepository.observeAlbum(_albumId)

    private val _albumTracksFlow: Flow<List<SimplifiedTrack>> = musicRepository.observeAlbumTracks(_albumId)

    val uiState: StateFlow<AlbumUiState> =
        combine(_albumFlow, _albumTracksFlow) { album, tracks ->
            AlbumUiState.Success(
                Album(
                id = album.id,
                    type = album.type,
                    imageUrl = album.imageUrl,
                    name = album.name,
                    artists = album.artists,
                    tracks = tracks)
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AlbumUiState.Loading)
}

sealed interface AlbumUiState {
    data object Loading: AlbumUiState
    data class Success(
        val album: Album,
    ): AlbumUiState
}
