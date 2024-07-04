package com.example.mymusic.feature.library

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.MyMusicGradientBackground
import com.example.mymusic.core.ui.ScreenHeader
import com.example.mymusic.core.ui.Sort
import com.example.mymusic.core.ui.SortBottomSheet
import com.example.mymusic.core.ui.SortOption
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.core.designSystem.util.lerpScrollOffset
import com.example.mymusic.core.designSystem.util.rememberScrollState
import com.example.mymusic.core.ui.AlbumCard
import com.example.mymusic.core.ui.PlaylistCard
import com.example.mymusic.core.ui.PreviewParameterData
import com.example.mymusic.model.SimplifiedAlbum
import com.example.mymusic.model.SimplifiedPlaylist
import kotlin.math.max

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    onPlaylistClick: (String) -> Unit,
    onNavigateToAlbum: (String) -> Unit,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        LibraryUiState.Loading -> Unit
        is LibraryUiState.Success -> {
            LibraryContent(
                albums = (uiState as LibraryUiState.Success).savedAlbums,
                playlists = (uiState as LibraryUiState.Success).savedPlaylists,
                onSortOptionChanged =  { (uiState as LibraryUiState.Success).currentSortOption.value = it },
                onPlaylistClick = onPlaylistClick,
                onNavigateToAlbumClick = onNavigateToAlbum,
                onAlbumClick = viewModel::onAlbumClick,
                currentSortOption = (uiState as LibraryUiState.Success).currentSortOption.value,
                modifier = modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun LibraryContent(
    albums: List<SimplifiedAlbum>,
    playlists: List<SimplifiedPlaylist>,
    onSortOptionChanged: (SortOption) -> Unit,
    onPlaylistClick: (String) -> Unit,
    onNavigateToAlbumClick: (String) -> Unit,
    currentSortOption: SortOption,
    modifier: Modifier = Modifier,
    onAlbumClick: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val scrollState = rememberScrollState(state = lazyListState)

    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        SortBottomSheet(
            currentOption = currentSortOption,
            onDismissRequest = { showBottomSheet = false },
            onSelection = onSortOptionChanged
        )
    }

    val textAlpha: Float by animateFloatAsState(
        if (scrollState.value >= 200) 1f else 0.0f,
        animationSpec = tween(500, easing = LinearOutSlowInEasing
        ), label = "library:textAlpha"
    )

    val dividerAlpha: Float by animateFloatAsState(
        if (scrollState.value >= 300) 1f else 0.0f,
        animationSpec = tween(800, easing = LinearOutSlowInEasing
        ), label = "library:dividerAlpha"
    )

    MyMusicGradientBackground(modifier = modifier) {
        Box {
            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    end = 16.dp,
                    start = 16.dp,
                    bottom = dimensionResource(id = R.dimen.player_with_bottom_app_bar_height)
                )
            ) {
                item {
                    Column(modifier = Modifier.alpha(
                        max(
                            0.0f,
                            lerpScrollOffset(
                                scrollState = scrollState,
                                valueMin = 0f,
                                valueMax = 250f,
                                reverse = true
                            )
                        )
                    )) {
                        ScreenHeader(
                            titleRes = R.string.your_library,
                            imageUrl = ""
                        )

                        Sort(
                            sortOption = currentSortOption,
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    showBottomSheet = true
                                }
                        )
                    }
                }
                items(items = playlists) { playlist ->
                    PlaylistCard(
                        name = playlist.name,
                        ownerName = playlist.ownerName,
                        imageUrl = playlist.imageUrl,
                        onClick = { onPlaylistClick(playlist.id) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                items(items = albums) { album ->
                    AlbumCard(
                        name = album.name,
                        artists = album.artists,
                        imageUrl = album.imageUrl,
                        onClick = {
                            onAlbumClick(album.id)
                            onNavigateToAlbumClick(album.id) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            TopAppBar(
                dividerAlpha = dividerAlpha,
                textAlpha = textAlpha,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.tertiary
                            .darker(0.9f)
                            .copy(alpha = textAlpha)
                    )
            )
        }
    }
}

@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier,
    textAlpha: Float  = 1.0f,
    dividerAlpha: Float  = 1.0f
) {
    Column(modifier = modifier
        .fillMaxWidth()
    ) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.your_library),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .wrapContentSize()
                    .alpha(textAlpha)
            )
        }
        HorizontalDivider(modifier = Modifier.alpha(dividerAlpha))
    }
}

@Preview
@Composable
fun LibraryPreview() {
    MyMusicTheme {
        LibraryContent(
            albums = PreviewParameterData.albums,
            playlists = PreviewParameterData.simplifiedPlaylists,
            onSortOptionChanged = {},
            onPlaylistClick = {},
            onNavigateToAlbumClick = {},
            currentSortOption = SortOption.RECENTLY_ADDED,
            onAlbumClick = {}
        )
    }
}