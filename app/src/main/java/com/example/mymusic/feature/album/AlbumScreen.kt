package com.example.mymusic.feature.album

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.absoluteOffset
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.constrainedMap
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.NetworkImage
import com.example.mymusic.core.designSystem.component.linearGradientScrim
import com.example.mymusic.core.designSystem.icon.MyMusicIcons
import com.example.mymusic.core.designSystem.theme.DynamicThemePrimaryColorsFromImage
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.theme.rememberDominantColorState
import com.example.mymusic.core.designSystem.util.contrastAgainst
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.core.designSystem.util.saturation
import com.example.mymusic.core.model.SimplifiedTrack
import com.example.mymusic.core.ui.PreviewParameterData
import com.example.mymusic.core.ui.PreviewWithBackground
import com.example.mymusic.core.ui.artistsString
import com.example.mymusic.core.ui.rememberCurrentOffset
import kotlin.math.min

@Composable
fun AlbumScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: AlbumViewModel = AlbumViewModel()
) {
    AlbumScreenContent(
        name = viewModel.currentAlbum.name,
        imageUrl = viewModel.currentAlbum.imageUrl,
        artists = artistsString(viewModel.currentAlbum.artists),
        tracks = viewModel.currentAlbum.tracks,
        onBackClick = onBackClick,
        modifier = modifier

    )
}

@SuppressLint("RestrictedApi")
@Composable
fun AlbumScreenContent(
    name: String,
    imageUrl: String,
    artists: String,
    tracks: List<SimplifiedTrack>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val scrollState = rememberCurrentOffset(state = lazyListState)

    val surfaceColor = MaterialTheme.colorScheme.surface
    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= 3f
    }

    val alpha: Float by animateFloatAsState(if (scrollState.value >= 500) 1f else 0.0f, label = "")

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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                state = lazyListState,
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        MaterialTheme.colorScheme.primary
                            .darker(0.95f)
                    )
            ) {
                item {
                    Box(modifier = Modifier
                        .graphicsLayer(alpha = 1 - min(
                            1.0f,
                            if (scrollState.value >= 300) constrainedMap(
                                0.0f,
                                1.0f,
                                300.0f,
                                800.0f,
                                scrollState.value.toFloat()
                            ) else 0.0f
                        ))) {
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
                                    color = MaterialTheme.colorScheme.primary.darker(0.95f),
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, 1000f)
                                )
                        )
                        AlbumHeaderWithContent(
                            name = name,
                            artists = artists,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        )

                    }
                }
                items(tracks) {track ->
                    TrackItem(
                        name = track.name,
                        artists = artistsString(track.artists),
                        onSettingsClick = { /*TODO*/ },
                        onTrackClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)

                    )
                }
            }


            TopAppBar(
                name = name,
                onBackPress = onBackClick,
                textAlpha = alpha,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.primary
                            .darker(0.9f)
                            .copy(
                                alpha = min(
                                    1.0f,
                                    if (scrollState.value >= 500) constrainedMap(
                                        0.0f,
                                        1.0f,
                                        500.0f,
                                        800.0f,
                                        scrollState.value.toFloat()
                                    ) else 0.0f
                                )
                            )
                    )
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
                .fillMaxSize()
                .height(400.dp)
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
                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.headlineLarge,
                        maxLines = 1,
                        modifier = Modifier.basicMarquee()
                    )
                    Text(
                        text = artists,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        modifier = Modifier
                            .alpha(0.5f)
                            .basicMarquee()
                    )
                }
                Row {
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(60.dp)) {
                        Icon(
                            imageVector = MyMusicIcons.Play,
                            contentDescription = stringResource(id = R.string.play),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(60.dp)) {
                        Icon(
                            imageVector = MyMusicIcons.Add,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
}

@Composable
private fun TopAppBar(
    name: String,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    textAlpha: Float = 1.0f,
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
            Spacer(modifier = Modifier.width(8.dp))
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
    }
}

@Composable
private fun TrackItem(
    name: String,
    artists: String,
    onSettingsClick: () -> Unit,
    onTrackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTrackClick() }
    ) {
        Column() {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = artists,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .alpha(0.5f)
            )
        }
        IconButton(onClick = onSettingsClick) {
            Icon(
                imageVector = MyMusicIcons.More,
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
fun AlbumScreenPreview(
    modifier: Modifier = Modifier
) {
    MyMusicTheme {
        val mockAlbum = PreviewParameterData.albums[0]
        AlbumScreenContent(
            name = mockAlbum.name,
            imageUrl = mockAlbum.imageUrl,
            artists = artistsString(mockAlbum.artists),
            tracks = mockAlbum.tracks,
            onBackClick = {}
        )
    }
}