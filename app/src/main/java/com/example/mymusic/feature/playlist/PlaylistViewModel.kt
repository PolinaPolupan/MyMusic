package com.example.mymusic.feature.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.OfflineFirstMusicRepository
import com.example.designsystem.component.OneOf
import com.example.designsystem.component.TracksListUiState
import com.example.model.Playlist
import com.example.model.SimplifiedPlaylist
import com.example.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    musicRepository: OfflineFirstMusicRepository
): ViewModel() {

    private val _playlistId: String = checkNotNull(savedStateHandle[PLAYLIST_ID_ARG])

    private val _playlistFlow: Flow<SimplifiedPlaylist> = musicRepository.observePlaylist(_playlistId)

    private val _playlistTracksFlow: Flow<List<Track>> = musicRepository.observePlaylistTracks(_playlistId)

    val uiState: StateFlow<TracksListUiState> =
        combine(_playlistFlow, _playlistTracksFlow) { playlist, tracks ->
            TracksListUiState.Success(
                item = OneOf(
                    playlist = Playlist(
                        id = playlist.id,
                        imageUrl = playlist.imageUrl,
                        name = playlist.name,
                        ownerName = playlist.ownerName,
                        tracks = tracks
                    )
                )
            )
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), TracksListUiState.Loading)
}
