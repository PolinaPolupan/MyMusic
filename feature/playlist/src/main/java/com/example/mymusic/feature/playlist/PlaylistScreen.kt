package com.example.mymusic.feature.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mymusic.core.designsystem.component.MyMusicIcons
import com.example.mymusic.core.designsystem.component.NetworkImage
import com.example.mymusic.core.designsystem.component.OneOf
import com.example.mymusic.core.designsystem.component.PreviewParameterData
import com.example.mymusic.core.designsystem.component.PreviewWithBackground
import com.example.mymusic.core.designsystem.component.TracksList
import com.example.mymusic.core.designsystem.component.TracksListUiState
import com.example.mymusic.core.designsystem.theme.MyMusicTheme
import com.example.mymusic.core.designsystem.util.artistsString
import com.example.mymusic.core.model.Track


@Composable
fun PlaylistScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PlaylistContent(
        uiState = uiState,
        onTrackClick = { /*TODO*/ },
        onSettingsClick = { /*TODO*/ },
        onBackClick = onBackClick,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun PlaylistContent(
    uiState: TracksListUiState,
    onTrackClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TracksList<Track>(
        uiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier,
        content = {
            TrackItem(
                track = it,
                onSettingsClick = onSettingsClick,
                onTrackClick = onTrackClick
            )
        }
    )
}

@Composable
private fun TrackItem(
    track: Track,
    onTrackClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clickable { onTrackClick(track.id) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            NetworkImage(
                imageUrl = track.album.imageUrl,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                Text(
                    text = track.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = artistsString(track.artists),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .alpha(0.5f)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onSettingsClick) {
            Icon(
                imageVector = MyMusicIcons.More,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.more)
            )
        }
    }
}

@PreviewWithBackground
@Composable
fun TrackItemPreview() {
    MyMusicTheme {
        TrackItem(
            track = PreviewParameterData.tracks[0],
            onSettingsClick = {},
            onTrackClick = {}
        )
    }
}

@Preview
@Composable
fun PlaylistScreenLoadingPreview() {
    MyMusicTheme {
        PlaylistContent(
            uiState = TracksListUiState.Loading,
            onTrackClick = {},
            onSettingsClick = {},
            onBackClick = {}
        )
    }
}

@Preview
@Composable
fun PlaylistScreenPreview() {
    MyMusicTheme {
        val playlist = PreviewParameterData.playlists[0]
        PlaylistContent(
            uiState = TracksListUiState.Success(item = OneOf(playlist = playlist)),
            onTrackClick = {},
            onSettingsClick = {},
            onBackClick = {}
        )
    }
}