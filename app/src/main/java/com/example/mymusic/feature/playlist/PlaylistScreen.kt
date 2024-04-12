package com.example.mymusic.feature.playlist

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.NetworkImage
import com.example.mymusic.core.designSystem.component.linearGradientScrim
import com.example.mymusic.core.designSystem.icon.MyMusicIcons
import com.example.mymusic.core.designSystem.theme.DynamicThemePrimaryColorsFromImage
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.theme.rememberDominantColorState
import com.example.mymusic.core.designSystem.util.contrastAgainst
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.core.designSystem.util.lerpScrollOffset
import com.example.mymusic.core.model.Artist
import com.example.mymusic.core.model.Track
import com.example.mymusic.core.ui.PreviewParameterData
import com.example.mymusic.core.ui.PreviewWithBackground
import com.example.mymusic.core.ui.artistsString
import com.example.mymusic.core.ui.rememberScrollState

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: PlaylistViewModel = PlaylistViewModel()
) {
    PlaylistScreenContent(
        name = viewModel.playlist.name,
        imageUrl = viewModel.playlist.imageUrl,
        ownerName = viewModel.playlist.ownerName,
        tracks = viewModel.playlist.tracks,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun PlaylistScreenContent(
    name: String,
    imageUrl: String,
    ownerName: String,
    tracks: List<Track>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val scrollState = rememberScrollState(state = lazyListState)

    val surfaceColor = MaterialTheme.colorScheme.surface
    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= 3f
    }

    // Top app bar text alpha dynamically changes with scrolling
    val textAlpha: Float by animateFloatAsState(
        if (scrollState.value >= 600) 1f else 0.0f,
        animationSpec = tween(500, easing = LinearOutSlowInEasing
        ), label = "album:textAlpha"
    )
    // Top app bar divider alpha dynamically changes with scrolling.
    // The Divider should appear later than the other elements, so the offset is bigger
    val dividerAlpha: Float by animateFloatAsState(
        if (scrollState.value >= 800) 1f else 0.0f,
        animationSpec = tween(500, easing = LinearOutSlowInEasing
        ), label = "album:dividerAlpha"
    )
    DynamicThemePrimaryColorsFromImage {
        // When the selected image url changes, call updateColorsFromImageUrl() or reset()

        /* TODO: Check if the updated color is correct */
        LaunchedEffect(imageUrl) {
            dominantColorState.updateColorsFromImageUrl(imageUrl)
        }

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = modifier
        ) {
            PlaylistContent(
                name = name,
                imageUrl = imageUrl,
                ownerName = ownerName,
                tracks = tracks,
                lazyListState = lazyListState,
                scrollState = scrollState
            )

            TopAppBar(
                name = name,
                onBackClick = onBackClick,
                textAlpha = textAlpha,
                dividerAlpha = dividerAlpha,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.primary
                            .darker(0.92f)
                            .copy(alpha = lerpScrollOffset(scrollState, 500f, 800f))
                    )
                    .testTag("album:topAppBar")
            )
        }
    }
}

@Composable
fun PlaylistContent(
    name: String,
    imageUrl: String,
    ownerName: String,
    tracks: List<Track>,
    lazyListState: LazyListState,
    scrollState: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyListState,
        modifier = modifier
            .fillMaxHeight()
            .background(
                MaterialTheme.colorScheme.primary
                    .darker(0.92f)
            )
            .testTag("album:lazyColumn")
    ) {
        item {
            Box(modifier = Modifier
                .graphicsLayer(alpha = lerpScrollOffset(scrollState, 400f, 800f, reverse = true))
            ) {
                NetworkImage(
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .size(400.dp)
                        .graphicsLayer {
                            translationY = -scrollState.value * 0.1f
                        }
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .linearGradientScrim(
                            color = MaterialTheme.colorScheme.primary.darker(0.92f),
                            start = Offset(0f, 0f),
                            end = Offset(0f, 1000f)
                        )
                )
                PlaylistHeaderWithContent(
                    name = name,
                    ownerName = ownerName,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                )
            }
        }
        items(tracks) {track ->
            TrackItem(
                name = track.name,
                artists = track.artists,
                imageUrl = track.album.imageUrl,
                onSettingsClick = { /*TODO*/ },
                onTrackClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(horizontal = 16.dp)

            )
        }
    }
}

@Composable
fun PlaylistHeaderWithContent(
    name: String,
    ownerName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .height(400.dp)
    ) {
        Spacer(modifier = Modifier
            .fillMaxHeight()
            .weight(4f)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(6f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                )
                Text(
                    text = ownerName,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .alpha(0.5f)
                )
            }
            Row(modifier = Modifier.weight(1f)) {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(60.dp)) {
                    Icon(
                        imageVector = MyMusicIcons.Play,
                        contentDescription = stringResource(id = R.string.play),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
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
    onBackClick: () -> Unit,
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
            IconButton(onClick = onBackClick, modifier = Modifier.size(30.dp)) {
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
            onBackClick = { /*TODO*/ },
            name = "Long long long long long long long name"
        )
    }
}

@Preview
@Composable
fun PlaylistScreenPreview() {
    MyMusicTheme {
        val playlist = PreviewParameterData.playlists[0]
        PlaylistScreenContent(
            name = playlist.name,
            imageUrl = playlist.imageUrl,
            ownerName = playlist.ownerName,
            tracks = playlist.tracks,
            onBackClick = {}
        )
    }
}