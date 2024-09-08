package com.example.mymusic.feature.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.repository.OfflineFirstMusicRepository
import com.example.mymusic.core.designSystem.component.OneOf
import com.example.mymusic.core.designSystem.component.TracksListUiState
import com.example.model.Album
import com.example.model.SimplifiedAlbum
import com.example.model.SimplifiedTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val musicRepository: OfflineFirstMusicRepository
) : ViewModel() {

    private val _albumId: String = checkNotNull(savedStateHandle[ALBUM_ID_ARG])

    private val _albumFlow: Flow<com.example.model.SimplifiedAlbum> = musicRepository.observeAlbum(_albumId)

    private val _albumTracksFlow: Flow<List<com.example.model.SimplifiedTrack>> = musicRepository.observeAlbumTracks(_albumId)

    val uiState: StateFlow<TracksListUiState> =
        combine(_albumFlow, _albumTracksFlow) { album, tracks ->
            TracksListUiState.Success(
                item = OneOf(
                    album = com.example.model.Album(
                        id = album.id,
                        type = album.type,
                        imageUrl = album.imageUrl,
                        name = album.name,
                        artists = album.artists,
                        tracks = tracks
                    )
                )
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), TracksListUiState.Loading)

    fun loadTrack(id: String) {
        viewModelScope.launch {
            musicRepository.loadTrack(id)
        }
    }
}
