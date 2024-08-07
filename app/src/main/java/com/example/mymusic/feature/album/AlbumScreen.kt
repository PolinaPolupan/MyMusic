package com.example.mymusic.feature.album

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.NetworkImage
import com.example.mymusic.core.designSystem.component.linearGradientScrim
import com.example.mymusic.core.designSystem.component.MyMusicIcons
import com.example.mymusic.core.designSystem.theme.DynamicThemePrimaryColorsFromImage
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.theme.rememberDominantColorState
import com.example.mymusic.core.designSystem.util.artistsString
import com.example.mymusic.core.designSystem.util.contrastAgainst
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.core.designSystem.util.lerpScrollOffset
import com.example.mymusic.model.SimplifiedArtist
import com.example.mymusic.model.SimplifiedTrack
import com.example.mymusic.core.designSystem.component.PreviewParameterData
import com.example.mymusic.core.designSystem.component.PreviewWithBackground
import com.example.mymusic.core.designSystem.util.rememberScrollState

@Composable
fun AlbumScreen(
    onNavigateToPlayer: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        /*TODO: Create loading screen*/
        AlbumUiState.Loading -> Unit
        is AlbumUiState.Success ->
            AlbumScreenContent(
                name = (uiState as AlbumUiState.Success).album.name,
                imageUrl = (uiState as AlbumUiState.Success).album.imageUrl,
                artists = (uiState as AlbumUiState.Success).album.artists,
                tracks = (uiState as AlbumUiState.Success).album.tracks,
                onNavigateToPlayer = onNavigateToPlayer,
                onTrackClick = viewModel::loadTrack,
                onBackClick = onBackClick,
                modifier = modifier,
            )
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun AlbumScreenContent(
    name: String,
    imageUrl: String,
    artists: List<SimplifiedArtist>,
    tracks: List<SimplifiedTrack>,
    onNavigateToPlayer: (String) -> Unit,
    onTrackClick: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
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

    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        // When the selected image url changes, call updateColorsFromImageUrl() or reset()

        /* TODO: Check if the updated color is correct */
        LaunchedEffect(imageUrl) {
            dominantColorState.updateColorsFromImageUrl(imageUrl)
        }
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = modifier
        ) {
            AlbumContent(
                name = name,
                imageUrl = imageUrl,
                artists = artists,
                tracks = tracks,
                lazyListState = lazyListState,
                scrollState = scrollState,
                onNavigateToPlayer = onNavigateToPlayer,
                onTrackClick = onTrackClick
            )
            TopAppBar(
                name = name,
                onBackPress = onBackClick,
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
fun AlbumContent(
    name: String,
    imageUrl: String,
    artists: List<SimplifiedArtist>,
    tracks: List<SimplifiedTrack>,
    lazyListState: LazyListState,
    scrollState: MutableState<Int>,
    onNavigateToPlayer: (String) -> Unit,
    onTrackClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        state = lazyListState,
        contentPadding = PaddingValues(
            bottom = dimensionResource(id = R.dimen.player_with_bottom_app_bar_height)
        ),
        modifier = modifier
            .fillMaxHeight()
            .background(
                MaterialTheme.colorScheme.primary
                    .darker(0.92f)
            )
            .testTag("album:lazyColumn")
    ) {
        item {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
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
                AlbumHeaderWithContent(
                    name = name,
                    artists = artistsString(artists),
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
                onSettingsClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onTrackClick(track.id)
                        onNavigateToPlayer(track.id)
                    }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumHeaderWithContent(
    name: String,
    artists: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(400.dp) // Add height to add space for the image
    ) {
        Spacer(modifier = Modifier
            .fillMaxHeight()
            .weight(4f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Column(modifier = Modifier.fillMaxWidth((.65f))) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    modifier = Modifier.basicMarquee()
                )
                Text(
                    text = artists,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    modifier = Modifier
                        .alpha(0.5f)
                        .basicMarquee()
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        imageVector = MyMusicIcons.Play,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(id = R.string.play),
                        modifier = Modifier.fillMaxSize()
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        imageVector = MyMusicIcons.Add,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
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
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .wrapContentSize()
                    .graphicsLayer(alpha = textAlpha)

            )
        }
        HorizontalDivider(modifier = Modifier.alpha(dividerAlpha))
    }
}

@Composable
private fun TrackItem(
    name: String,
    artists: List<SimplifiedArtist>,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.9f)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = artistsString(artists),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .alpha(0.5f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = onSettingsClick
        ) {
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
fun TopAppBarPreview() {
    MyMusicTheme {
        TopAppBar(onBackPress = { /*TODO*/ }, name = "Long long long long long long long name")
    }
}

@Preview
@Composable
fun AlbumScreenPreview() {
    MyMusicTheme {
        val mockAlbum = PreviewParameterData.simplifiedAlbums[0]
        val mockTracks = PreviewParameterData.simplifiedTracks
        AlbumScreenContent(
            name = mockAlbum.name,
            imageUrl = mockAlbum.imageUrl,
            artists = mockAlbum.artists,
            tracks = mockTracks,
            onNavigateToPlayer = {},
            onBackClick = {},
            onTrackClick = {}
        )
    }
}

@Preview
@Composable
fun AlbumHeaderPreview() {
    MyMusicTheme {
        AlbumHeaderWithContent(
            name = "Long long long long long long long name",
            artists = "Long long long long long long long name"
        )
    }
}

@Preview
@Composable
fun TrackItemPreview() {
    MyMusicTheme {
        val artists = PreviewParameterData.simplifiedArtists
        TrackItem(
            name = "Long long long long long long long long long long long name",
            artists = artists,
            onSettingsClick = {}
        )
    }
}