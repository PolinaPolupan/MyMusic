package com.example.mymusic.feature.addToPlaylist

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.BlurredImageHeader
import com.example.mymusic.core.designSystem.component.Sort
import com.example.mymusic.core.designSystem.component.SortBottomSheet
import com.example.mymusic.core.designSystem.component.SortOption
import com.example.mymusic.core.designSystem.component.linearGradientScrim
import com.example.mymusic.core.designSystem.component.MyMusicIcons
import com.example.mymusic.core.designSystem.theme.DynamicThemePrimaryColorsFromImage
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.theme.rememberDominantColorState
import com.example.mymusic.core.designSystem.util.contrastAgainst
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.core.designSystem.util.lerpScrollOffset
import com.example.mymusic.core.designSystem.util.rememberScrollState
import com.example.mymusic.model.Track
import com.example.mymusic.core.designSystem.component.PreviewParameterData
import com.example.mymusic.feature.library.playlistsList
import com.example.mymusic.model.SimplifiedPlaylist
import kotlinx.coroutines.flow.flowOf

@Composable
fun AddToPlayListScreen(
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddToPlaylistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        /*TODO: Add loading screen*/
        AddToPlaylistUiState.Loading -> Unit
        is AddToPlaylistUiState.Success -> {

            val playlists = viewModel.savedPlaylists.collectAsLazyPagingItems()

            AddToPlayListContent(
                track = (uiState as AddToPlaylistUiState.Success).track,
                playlists = playlists,
                onSortOptionChanged = viewModel::setSortOption,
                currentSortOption = viewModel.currentSortOption.value,
                onBackPress = onBackPress,
                modifier = modifier
                    .fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddToPlayListContent(
    track: Track,
    playlists: LazyPagingItems<SimplifiedPlaylist>,
    onBackPress: () -> Unit,
    onSortOptionChanged: (SortOption) -> Unit,
    currentSortOption: SortOption,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val scrollState = rememberScrollState(state = lazyListState)

    val surfaceColor = MaterialTheme.colorScheme.surface
    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= 3f
    }

    val alpha: Float by animateFloatAsState(
        if (scrollState.value >= 140) 1f else 0.0f,
        animationSpec = tween(500, easing = LinearOutSlowInEasing
        ), label = "album:textAlpha"
    )

    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        // When the selected image url changes, call updateColorsFromImageUrl() or reset()
        LaunchedEffect(track.album.imageUrl) {
            dominantColorState.updateColorsFromImageUrl(track.album.imageUrl)
        }

        Box(
            modifier = modifier.linearGradientScrim(
                color = MaterialTheme.colorScheme.primary.darker(0.9f),
                end = Offset(0f, 700f)
            )
        ) {
            var showBottomSheet by remember { mutableStateOf(false) }

            if (showBottomSheet) {
                SortBottomSheet(
                    currentOption = currentSortOption,
                    onDismissRequest = { showBottomSheet = false },
                    onSelection = onSortOptionChanged,
                    containerColor = MaterialTheme.colorScheme.primary.darker(0.75f)
                )
            }

            BlurredImageHeader(
                imageUrl = track.album.imageUrl,
                alpha = lerpScrollOffset(scrollState, 0f, 200f, reverse = true),
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )

            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    end = 16.dp,
                    start = 16.dp
                )
            ) {
                stickyHeader {
                    TopAppBar(
                        onBackPress = onBackPress,
                        dividerAlpha = alpha,
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.primary
                                .darker(0.9f)
                                .copy(alpha = alpha)
                            )
                    )
                }
                item {
                    Sort(
                        sortOption = currentSortOption,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .clickable {
                                showBottomSheet = true
                            }
                    )
                }
                playlistsList(playlists = playlists, onPlaylistClick = {}, onNavigateToPlaylistClick = {})
                item {
                    Spacer(modifier = Modifier.height(200.dp))
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(200.dp)
                    .linearGradientScrim(
                        color = Color.Black
                    )
            ) {
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(32.dp)
                ) {
                    Text(text = stringResource(id = R.string.done))
                }
            }
        }
    }
}

@Composable
private fun TopAppBar(
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    dividerAlpha: Float = 1f
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
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val text = createRef()
                IconButton(onClick = onBackPress, modifier = Modifier.size(30.dp)) {
                    Icon(
                        imageVector = MyMusicIcons.ArrowBack,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = stringResource(id = R.string.add_to_playlist),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(text) {
                            centerHorizontallyTo(parent)
                        }
                )
        }
    }
        HorizontalDivider(modifier = Modifier.alpha(dividerAlpha))
}
}

@Preview
@Composable
fun AddToPlayListPreview() {
    MyMusicTheme {
        AddToPlayListContent(
            track = PreviewParameterData.tracks[0],
            playlists = flowOf(PagingData.from(PreviewParameterData.simplifiedPlaylists)).collectAsLazyPagingItems(),
            currentSortOption = SortOption.RECENTLY_ADDED,
            onSortOptionChanged = {},
            onBackPress = {}
        )
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    MyMusicTheme {
        TopAppBar(onBackPress = {})
    }
}