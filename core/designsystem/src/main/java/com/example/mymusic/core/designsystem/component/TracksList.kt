package com.example.mymusic.core.designsystem.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.mymusic.core.designsystem.R
import com.example.mymusic.core.designsystem.theme.DynamicThemePrimaryColorsFromImage
import com.example.mymusic.core.designsystem.theme.MyMusicTheme
import com.example.mymusic.core.designsystem.theme.rememberDominantColorState
import com.example.mymusic.core.designsystem.util.artistsString
import com.example.mymusic.core.designsystem.util.darker
import com.example.mymusic.core.designsystem.util.lerpScrollOffset
import com.example.mymusic.core.model.Album
import com.example.mymusic.core.model.Playlist
import kotlin.math.abs

@Composable
fun <T> TracksList(
    uiState: TracksListUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    val lazyListState = rememberLazyListState()

    val firstVisibleItemScrollOffset by remember {
        derivedStateOf { lazyListState.firstVisibleItemScrollOffset }
    }

    val firstVisibleItemIndex by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex }
    }

    val dominantColorState = rememberDominantColorState()

    // Top app bar text alpha dynamically changes with scrolling
    val textAlpha: Float by animateFloatAsState(
        if (abs(firstVisibleItemScrollOffset) >= 600 || firstVisibleItemIndex > 0) 1f else 0.0f,
        animationSpec = tween(500, easing = LinearOutSlowInEasing),
        label = "tracksList:textAlpha"
    )
    // Top app bar divider alpha dynamically changes with scrolling.
    // The Divider should appear later than the other elements, so the offset is bigger
    val dividerAlpha: Float by animateFloatAsState(
        if (abs(firstVisibleItemScrollOffset) >= 800 || firstVisibleItemIndex > 0) 1f else 0.0f,
        animationSpec = tween(500, easing = LinearOutSlowInEasing),
        label = "tracksList:dividerAlpha"
    )
    DynamicThemePrimaryColorsFromImage {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = modifier
        ) {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(bottom = dimensionResource(id = R.dimen.player_with_bottom_app_bar_height)),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primary.darker(0.92f))
                    .testTag("tracksList:lazyColumn")
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .graphicsLayer(
                                alpha = lerpScrollOffset(
                                    scrollOffset = abs(firstVisibleItemScrollOffset),
                                    valueMin = 400f,
                                    valueMax = 800f,
                                    reverse = true
                                )
                            )
                    ) {
                        if (uiState is TracksListUiState.Success) {
                            val imageUrl = if (uiState.item.album != null) {
                                uiState.item.album.imageUrl
                            } else {
                                uiState.item.playlist!!.imageUrl
                            }

                            LaunchedEffect(imageUrl) { dominantColorState.updateColorsFromImageUrl(imageUrl) }

                            NetworkImage(
                                imageUrl = imageUrl,
                                modifier = Modifier
                                    .size(400.dp)
                                    .graphicsLayer {translationY = -firstVisibleItemScrollOffset * 0.1f }
                            )
                        }
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .linearGradientScrim(
                                    color = MaterialTheme.colorScheme.primary.darker(0.92f),
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, 1000f)
                                )
                        )
                        CoverHeader(
                            uiState = uiState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
                when (uiState) {
                    TracksListUiState.Loading -> {
                        items(count = 5) {
                            AnimationBox {
                                RectangleRoundedCornerPlaceholder(
                                    width = dimensionResource(id = R.dimen.player_card_width),
                                    height = 70.dp,
                                    cornerSize = 12.dp,
                                    modifier = Modifier
                                        .testTag("tracksList:itemsLoading")
                                        .padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                    is TracksListUiState.Success -> {
                        // Item is either album or playlist
                        val tracks: List<T> = if (uiState.item.album != null) {
                            uiState.item.album.tracks as List<T>
                        } else {
                            uiState.item.playlist!!.tracks as List<T>
                        }
                        items(tracks) { track -> content(track) }
                    }
                }
            }
            TopAppBar(uiState = uiState, onBackClick = onBackClick, textAlpha = textAlpha, dividerAlpha = dividerAlpha)
        }
    }
}

@Composable
private fun TopAppBar(
    uiState: TracksListUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    textAlpha: Float = 1.0f,
    dividerAlpha: Float = 1.0f,
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary.darker(0.92f).copy(alpha = textAlpha))
        .testTag("tracksList:topAppBar")
    ) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = MyMusicIcons.ArrowBack,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            when (uiState) {
                TracksListUiState.Loading -> {
                    RectangleRoundedCornerPlaceholder(modifier = Modifier
                        .height(30.dp)
                        .testTag("tracksList:topAppBarLoading")
                    )
                }
                is TracksListUiState.Success -> {
                    val name: String = if (uiState.item.album != null) {
                        uiState.item.album.name
                    } else {
                        uiState.item.playlist!!.name
                    }
                    Text(
                        text = name,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .wrapContentSize()
                            .graphicsLayer(alpha = textAlpha)
                    )
                }
            }
        }
        HorizontalDivider(modifier = Modifier.alpha(dividerAlpha))
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CoverHeader(
    uiState: TracksListUiState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.height(400.dp)) {
        Spacer(modifier = Modifier
            .fillMaxHeight()
            .weight(4f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Column(modifier = Modifier.fillMaxWidth(.65f)) {
                when (uiState) {
                    TracksListUiState.Loading -> {
                        RectangleRoundedCornerPlaceholder(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3f)
                                .testTag("tracksList:nameTextPlaceholder")
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        RectangleRoundedCornerPlaceholder(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .testTag("tracksList:descriptionTextPlaceholder")
                        )
                    }
                    is TracksListUiState.Success -> {
                        val name: String
                        val description: String

                        if (uiState.item.album != null) {
                            name = uiState.item.album.name
                            description = artistsString(uiState.item.album.artists)
                        } else {
                            name = uiState.item.playlist!!.name
                            description = uiState.item.playlist.ownerName
                        }
                        Text(
                            text = name,
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            modifier = Modifier.basicMarquee()
                        )
                        Text(
                            text = description,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            modifier = Modifier
                                .alpha(0.5f)
                                .basicMarquee()
                        )
                    }
                }
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

sealed interface TracksListUiState {

    data object Loading: TracksListUiState

    data class Success(
        val item: OneOf,
    ): TracksListUiState
}

data class OneOf(
    val playlist: Playlist? = null,
    val album: Album? = null
)

@PreviewWithBackground
@Composable
fun TopAppBarLoadingPreview() {
    MyMusicTheme {
        TopAppBar(
            uiState = TracksListUiState.Loading,
            onBackClick = { /*TODO*/ },
        )
    }
}

@PreviewWithBackground
@Composable
fun TopAppBarPreview() {
    MyMusicTheme {
        TopAppBar(
            uiState = TracksListUiState.Success(item = OneOf(playlist = PreviewParameterData.playlists[0])),
            onBackClick = { /*TODO*/ },
        )
    }
}

@Preview
@Composable
fun CoverHeaderLoadingPreview() {
    MyMusicTheme {
        CoverHeader(
            uiState = TracksListUiState.Loading,
        )
    }
}

@Preview
@Composable
fun CoverHeaderPreview() {
    MyMusicTheme {
        CoverHeader(
            uiState = TracksListUiState.Success(item = OneOf(playlist = PreviewParameterData.playlists[0])),
        )
    }
}