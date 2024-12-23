package com.example.mymusic.feature.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.appremote.SpotifyAppRemoteManager
import com.example.mymusic.core.data.repository.MusicRepository
import com.example.mymusic.core.data.repository.UserDataRepository
import com.example.mymusic.core.designsystem.component.OneOf
import com.example.mymusic.core.designsystem.component.TracksListUiState
import com.example.mymusic.core.model.Playlist
import com.example.mymusic.core.model.SimplifiedPlaylist
import com.example.mymusic.core.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val appRemoteManager: SpotifyAppRemoteManager,
    savedStateHandle: SavedStateHandle,
    musicRepository: MusicRepository
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

    fun onTrackClick(isPlaying: Boolean, track: Track) {
        viewModelScope.launch {
            userDataRepository.setIsPlaying(isPlaying)
            userDataRepository.setTrackId(track.id)
        }
        appRemoteManager.play(track.uri)
    }
}
