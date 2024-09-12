package com.example.album

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.MyMusicIcons
import com.example.designsystem.component.OneOf
import com.example.designsystem.component.PreviewParameterData
import com.example.designsystem.component.TracksList
import com.example.designsystem.component.TracksListUiState
import com.example.designsystem.theme.MyMusicTheme
import com.example.designsystem.util.artistsString
import com.example.model.SimplifiedTrack

@Composable
fun AlbumScreen(
    onNavigateToPlayer: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AlbumContent(
        uiState = uiState,
        onTrackClick = {
            viewModel.loadTrack(it)
            onNavigateToPlayer(it)
        },
        onSettingsClick = { /*TODO*/ },
        onBackClick = onBackClick,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun AlbumContent(
    uiState: TracksListUiState,
    onTrackClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TracksList<com.example.model.SimplifiedTrack>(
        uiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier,
        content = { TrackItem(
            track = it,
            onSettingsClick = onSettingsClick,
            onTrackClick = onTrackClick)
        }
    )
}

@Composable
private fun TrackItem(
    track: com.example.model.SimplifiedTrack,
    onTrackClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clickable { onTrackClick(track.id) }
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.9f)) {
            Text(
                text = track.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = artistsString(track.artists),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.alpha(0.5f)
            )
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

@Preview
@Composable
fun AlbumScreenLoadingPreview() {
    MyMusicTheme {
        AlbumContent(
            uiState = TracksListUiState.Loading,
            onTrackClick = {},
            onSettingsClick = {},
            onBackClick = {})
    }
}

@Preview
@Composable
fun AlbumScreenPreview() {
    MyMusicTheme {
        val album = PreviewParameterData.albums[0]
        AlbumContent(
            uiState = TracksListUiState.Success(item = OneOf(album = album)),
            onTrackClick = {},
            onSettingsClick = {},
            onBackClick = {})
    }
}

@Preview
@Composable
fun TrackItemPreview() {
    MyMusicTheme {
        TrackItem(
            track = PreviewParameterData.simplifiedTracks[0],
            onTrackClick = {},
            onSettingsClick = {}
        )
    }
}