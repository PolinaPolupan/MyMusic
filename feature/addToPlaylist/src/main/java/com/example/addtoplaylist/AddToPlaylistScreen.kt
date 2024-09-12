package com.example.addtoplaylist

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.designsystem.component.AnimationBox
import com.example.designsystem.component.MyMusicIcons
import com.example.designsystem.component.PlaylistCard
import com.example.designsystem.component.PreviewParameterData
import com.example.designsystem.component.Sort
import com.example.designsystem.component.SortBottomSheet
import com.example.designsystem.component.SortOption
import com.example.designsystem.component.linearGradientScrim
import com.example.designsystem.component.BlurredImageHeader
import com.example.designsystem.theme.DominantColorState
import com.example.designsystem.theme.DynamicThemePrimaryColorsFromImage
import com.example.designsystem.theme.MyMusicTheme
import com.example.designsystem.theme.rememberDominantColorState
import com.example.designsystem.util.darker
import com.example.designsystem.util.lerpScrollOffset
import com.example.designsystem.component.RectangleRoundedCornerPlaceholder
import kotlinx.coroutines.flow.flowOf
import kotlin.math.abs

@Composable
fun AddToPlayListScreen(
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddToPlaylistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        AddToPlaylistUiState.Loading -> {

            AddToPlayListContent(
                uiState = uiState,
                playlists = flowOf(PagingData.from(emptyList<com.example.model.SimplifiedPlaylist>())).collectAsLazyPagingItems(),
                onSortOptionChanged = viewModel::setSortOption,
                currentSortOption = viewModel.currentSortOption.value,
                onBackPress = onBackPress,
                modifier = modifier.fillMaxSize()
            )
        }
        is AddToPlaylistUiState.Success -> {

            val playlists = viewModel.savedPlaylists.collectAsLazyPagingItems()

            AddToPlayListContent(
                uiState = uiState,
                playlists = playlists,
                onSortOptionChanged = viewModel::setSortOption,
                currentSortOption = viewModel.currentSortOption.value,
                onBackPress = onBackPress,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddToPlayListContent(
    uiState: AddToPlaylistUiState,
    playlists: LazyPagingItems<com.example.model.SimplifiedPlaylist>,
    onBackPress: () -> Unit,
    onSortOptionChanged: (SortOption) -> Unit,
    currentSortOption: SortOption,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    val firstVisibleItemScrollOffset by remember {
        derivedStateOf { lazyListState.firstVisibleItemScrollOffset }
    }

    val firstVisibleItemIndex by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex }
    }

    val dominantColorState = rememberDominantColorState()

    val alpha: Float by animateFloatAsState(
        if (abs(firstVisibleItemScrollOffset) >= 140 || firstVisibleItemIndex > 0) 1f else 0.0f,
        animationSpec = tween(500, easing = LinearOutSlowInEasing),
        label = "addToPlaylist:alpha"
    )

    DynamicThemePrimaryColorsFromImage(dominantColorState) {
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
                uiState = uiState,
                firstVisibleItemIndex = firstVisibleItemIndex,
                firstVisibleItemScrollOffset = firstVisibleItemScrollOffset,
                dominantColorState = dominantColorState
            )
            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight().testTag("addToPlaylist:items")
            ) {
                stickyHeader { TopAppBar(onBackPress = onBackPress, alpha = alpha) }
                item {
                    Sort(
                        sortOption = currentSortOption,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { showBottomSheet = true }
                    )
                }
                playlistsList(uiState = uiState, playlists = playlists)
                item { Spacer(modifier = Modifier.height(200.dp)) }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(200.dp)
                    .linearGradientScrim(color = Color.Black)
            ) {
                OutlinedButton(
                    onClick = { onBackPress() },
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
private fun BlurredImageHeader(
    uiState: AddToPlaylistUiState,
    firstVisibleItemIndex: Int,
    firstVisibleItemScrollOffset: Int,
    dominantColorState: DominantColorState,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        AddToPlaylistUiState.Loading -> Unit
        is AddToPlaylistUiState.Success -> {

            // When the selected image url changes, call updateColorsFromImageUrl() or reset()
            LaunchedEffect(uiState.track.album.imageUrl) {
                dominantColorState.updateColorsFromImageUrl(uiState.track.album.imageUrl)
            }

            BlurredImageHeader(
                imageUrl = uiState.track.album.imageUrl,
                alpha = if (firstVisibleItemIndex == 0) lerpScrollOffset(
                    scrollOffset = abs(firstVisibleItemScrollOffset),
                    valueMin = 0f,
                    valueMax = 200f,
                    reverse = true) - 0.2f else 0f,
                edgeTreatment = BlurredEdgeTreatment.Unbounded,
                modifier = modifier
            )
        }
    }
}

private fun LazyListScope.playlistsList(
    uiState: AddToPlaylistUiState,
    playlists: LazyPagingItems<com.example.model.SimplifiedPlaylist>,
) {
    when (uiState) {
        com.example.addtoplaylist.AddToPlaylistUiState.Loading -> {
            items(count = 5) {
                AnimationBox {
                    RectangleRoundedCornerPlaceholder(
                        width = dimensionResource(id = R.dimen.player_card_width),
                        height = 70.dp,
                        cornerSize = 12.dp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .testTag("addToPlaylist:playlistsLoading")
                    )
                }
            }
        }
        is AddToPlaylistUiState.Success -> {
            items(
                items = playlists.itemSnapshotList,
                key = { it?.id ?: "0" }
            ) { playlist ->
                // rememberSaveable is needed in order to save selection state during scrolling
                var isSelected by rememberSaveable { mutableStateOf(false) }
                AnimationBox {
                    PlaylistCard(
                        name = playlist!!.name,
                        ownerName = playlist.ownerName,
                        imageUrl = playlist.imageUrl,
                        isSelected = isSelected,
                        isSelectable = true,
                        onClick = { isSelected = !isSelected },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TopAppBar(
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    alpha: Float = 1f
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .background(Color.Black.copy(alpha = alpha))
    ) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("addToPlaylist:topAppBar"),
            ) {
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
                        .constrainAs(text) { centerHorizontallyTo(parent) }
                )
            }
        }
        HorizontalDivider(modifier = Modifier.alpha(alpha))
    }
}

@Preview
@Composable
fun AddToPlayListPreview() {
    MyMusicTheme {
        AddToPlayListContent(
            uiState = AddToPlaylistUiState.Success(PreviewParameterData.tracks[0]),
            playlists = flowOf(PagingData.from(PreviewParameterData.simplifiedPlaylists)).collectAsLazyPagingItems(),
            currentSortOption = SortOption.RECENTLY_ADDED,
            onSortOptionChanged = {},
            onBackPress = {}
        )
    }
}

@Preview
@Composable
fun AddToPlayListLoadingPreview() {
    MyMusicTheme {
        AddToPlayListContent(
            uiState = AddToPlaylistUiState.Loading,
            playlists = flowOf(PagingData.from(emptyList<com.example.model.SimplifiedPlaylist>())).collectAsLazyPagingItems(),
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