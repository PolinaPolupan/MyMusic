package com.example.mymusic.feature.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.NetworkImage
import com.example.mymusic.core.designSystem.icon.MyMusicIcons
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.model.Artist
import com.example.mymusic.core.model.Track
import com.example.mymusic.core.ui.PreviewParameterData
import com.example.mymusic.core.ui.PreviewWithBackground
import com.example.mymusic.core.ui.artistsString

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: PlaylistViewModel = PlaylistViewModel()
) {
    PlaylistContent(
        name = viewModel.playlist.name,
        imageUrl = viewModel.playlist.imageUrl,
        ownerName = viewModel.playlist.ownerName,
        tracks = viewModel.playlist.tracks,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun PlaylistContent(
    name: String,
    imageUrl: String,
    ownerName: String,
    tracks: List<Track>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

}

@Composable
private fun TrackItem(
    name: String,
    artists: List<Artist>,
    imageUrl: String,
    onSettingsClick: () -> Unit,
    onTrackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTrackClick() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            NetworkImage(
                imageUrl = imageUrl,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = artistsString(artists),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .alpha(0.5f)
                )
            }
        }
        IconButton(onClick = onSettingsClick) {
            Icon(
                imageVector = MyMusicIcons.More,
                contentDescription = stringResource(R.string.more)
            )
        }
    }
}

/* TODO: Write tests */
@Composable
private fun TopAppBar(
    name: String,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    textAlpha: Float = 1.0f,
    dividerAlpha: Float = 1.0f,
) {
    Column(modifier = modifier
        .fillMaxWidth()
    ) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = onBackPress, modifier = Modifier.size(30.dp)) {
                Icon(
                    imageVector = MyMusicIcons.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .wrapContentSize()
                    .graphicsLayer(alpha = textAlpha)

            )
        }
        Divider(modifier = Modifier.alpha(dividerAlpha))
    }
}

@PreviewWithBackground
@Composable
fun TrackItemPreview() {
    MyMusicTheme {
        val track = PreviewParameterData.tracks[0]
        TrackItem(
            name = track.name,
            imageUrl = track.album.imageUrl,
            artists = track.artists,
            onSettingsClick = { /*TODO*/ },
            onTrackClick = { /*TODO*/ })
    }
}

@PreviewWithBackground
@Composable
fun TopAppBarPreview() {
    MyMusicTheme {
        TopAppBar(
            onBackPress = { /*TODO*/ },
            name = "Long long long long long long long name"
        )
    }
}

@Preview
@Composable
fun PlaylistScreenPreview() {
    MyMusicTheme {
        val playlist = PreviewParameterData.playlists[0]
        PlaylistContent(
            name = playlist.name,
            imageUrl = playlist.imageUrl,
            ownerName = playlist.ownerName,
            tracks = playlist.tracks,
            onBackClick = {}
        )
    }
}