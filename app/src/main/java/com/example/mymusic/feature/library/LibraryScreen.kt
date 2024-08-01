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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.AnimationBox
import com.example.mymusic.core.designSystem.component.MyMusicGradientBackground
import com.example.mymusic.core.designSystem.component.ScreenHeader
import com.example.mymusic.core.designSystem.component.Sort
import com.example.mymusic.core.designSystem.component.SortBottomSheet
import com.example.mymusic.core.designSystem.component.SortOption
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.core.designSystem.util.lerpScrollOffset
import com.example.mymusic.core.designSystem.util.rememberScrollState
import com.example.mymusic.core.designSystem.component.AlbumCard
import com.example.mymusic.core.designSystem.component.PlaylistCard
import com.example.mymusic.core.designSystem.component.PreviewParameterData
import com.example.mymusic.core.designSystem.component.RectangleRoundedCornerPlaceholder
import com.example.mymusic.feature.home.AuthenticatedUiState
import com.example.mymusic.model.SimplifiedAlbum
import com.example.mymusic.model.SimplifiedPlaylist
import kotlinx.coroutines.flow.flowOf
import kotlin.math.max

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    onNavigateToPlaylist: (String) -> Unit,
    onNavigateToAlbum: (String) -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()
    val authenticatedUiState by viewModel.authenticatedUiState.collectAsStateWithLifecycle()

    // If not authorized - navigate to login screen
    if (authenticatedUiState is AuthenticatedUiState.NotAuthenticated) {
        LaunchedEffect(key1 = authenticatedUiState) {
            onNavigateToLogin()
        }
    }
    // Show ui if the data is ready and user is authorized
    else if (authenticatedUiState is AuthenticatedUiState.Success && !isSyncing) {

        val savedAlbums = viewModel.savedAlbums.collectAsLazyPagingItems()
        val savedPlaylists = viewModel.savedPlaylists.collectAsLazyPagingItems()

        LibraryContent(
            uiState = authenticatedUiState,
            userImageUrl = (authenticatedUiState as AuthenticatedUiState.Success).userImageUrl,
            albums = savedAlbums,
            playlists = savedPlaylists,
            onSortOptionChanged =  { viewModel.currentSortOption.value = it },
            onNavigateToPlaylist = onNavigateToPlaylist,
            onNavigateToAlbumClick = onNavigateToAlbum,
            currentSortOption = viewModel.currentSortOption.value,
            onAlbumClick = viewModel::onAlbumClick,
            onPlaylistClick = viewModel::onPlaylistClick,
            modifier = modifier
                .fillMaxSize(),
        )
    } else {
        LibraryContent(
            uiState = AuthenticatedUiState.Loading,
            userImageUrl = "",
            albums = flowOf(PagingData.from(emptyList<SimplifiedAlbum>())).collectAsLazyPagingItems(),
            playlists = flowOf(PagingData.from(emptyList<SimplifiedPlaylist>())).collectAsLazyPagingItems(),
            onSortOptionChanged =  { viewModel.currentSortOption.value = it },
            onNavigateToPlaylist = onNavigateToPlaylist,
            onNavigateToAlbumClick = onNavigateToAlbum,
            currentSortOption = viewModel.currentSortOption.value,
            onAlbumClick = viewModel::onAlbumClick,
            onPlaylistClick = viewModel::onPlaylistClick,
            modifier = modifier
                .fillMaxSize(),
        )
    }
}

@Composable
fun LibraryContent(
    uiState: AuthenticatedUiState,
    userImageUrl: String?,
    albums: LazyPagingItems<SimplifiedAlbum>,
    playlists: LazyPagingItems<SimplifiedPlaylist>,
    onSortOptionChanged: (SortOption) -> Unit,
    onNavigateToPlaylist: (String) -> Unit,
    onNavigateToAlbumClick: (String) -> Unit,
    currentSortOption: SortOption,
    modifier: Modifier = Modifier,
    onAlbumClick: (String) -> Unit,
    onPlaylistClick: (String) -> Unit
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

    val dissolveBackground: Float by animateFloatAsState(
        if (scrollState.value >= 100) 1f else 0.75f,
        animationSpec = tween(1000, easing = LinearOutSlowInEasing
        ), label = "library:dissolve"
    )

    MyMusicGradientBackground(
        modifier = modifier,
        darker = dissolveBackground
    ) {
        Box {
            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    end = 16.dp,
                    start = 16.dp,
                    bottom = dimensionResource(id = R.dimen.player_with_bottom_app_bar_height)
                ),
                modifier = Modifier.fillMaxSize()
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
                            imageUrl = userImageUrl,
                            isLoading = uiState is AuthenticatedUiState.Loading
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
                when (uiState) {
                    AuthenticatedUiState.Loading -> {
                        items(count = 5) {
                            AnimationBox(
                            ) {
                                RectangleRoundedCornerPlaceholder(
                                    width = dimensionResource(id = R.dimen.player_card_width),
                                    height = 70.dp,
                                    cornerSize = 12.dp
                                )
                            }
                        }
                    }
                    AuthenticatedUiState.NotAuthenticated -> {}
                    is AuthenticatedUiState.Success -> {
                        albumsList(albums, onNavigateToAlbumClick, onAlbumClick)
                        playlistsList(playlists, onNavigateToPlaylist, onPlaylistClick)
                    }
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




fun LazyListScope.albumsList(
    albums: LazyPagingItems<SimplifiedAlbum>,
    onNavigateToAlbumClick: (String) -> Unit,
    onAlbumClick: (String) -> Unit
) {

    items(
        items = albums.itemSnapshotList,
        key = { it?.id ?: "0" }
    ) {
        album ->
        AnimationBox {
            AlbumCard(
                name = album!!.name,
                artists = album.artists,
                imageUrl = album.imageUrl,
                onClick = {
                    onAlbumClick(album.id)
                    onNavigateToAlbumClick(album.id) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

fun LazyListScope.playlistsList(
    playlists: LazyPagingItems<SimplifiedPlaylist>,
    onNavigateToPlaylistClick: (String) -> Unit,
    onPlaylistClick: (String) -> Unit,
    isSelectable: Boolean = true,
) {

    items(
        items = playlists.itemSnapshotList,
        key = { it?.id ?: "0" }
    ) {
        playlist ->
        // rememberSaveable is needed in order to save selection state during scrolling
        var isSelected by rememberSaveable {
            mutableStateOf(false)
        }
        AnimationBox {
            PlaylistCard(
                name = playlist!!.name,
                ownerName = playlist.ownerName,
                imageUrl = playlist.imageUrl,
                isSelected = isSelected,
                isSelectable = isSelectable,
                onClick = {
                    isSelected = !isSelected
                    onPlaylistClick(playlist.id)
                    onNavigateToPlaylistClick(playlist.id)
                },
                modifier = Modifier
                    .fillMaxWidth()
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
            uiState = AuthenticatedUiState.Success(""),
            userImageUrl = "",
            albums = flowOf(PagingData.from(PreviewParameterData.simplifiedAlbums)).collectAsLazyPagingItems(),
            playlists = flowOf(PagingData.from(PreviewParameterData.simplifiedPlaylists)).collectAsLazyPagingItems(),
            onSortOptionChanged = {},
            onNavigateToPlaylist = {},
            onNavigateToAlbumClick = {},
            currentSortOption = SortOption.RECENTLY_ADDED,
            onAlbumClick = {},
            onPlaylistClick = {}
        )
    }
}

@Preview
@Composable
fun LibraryLoadingPreview() {
    MyMusicTheme {
        LibraryContent(
            uiState = AuthenticatedUiState.Loading,
            userImageUrl = "",
            albums = flowOf(PagingData.from(PreviewParameterData.simplifiedAlbums)).collectAsLazyPagingItems(),
            playlists = flowOf(PagingData.from(PreviewParameterData.simplifiedPlaylists)).collectAsLazyPagingItems(),
            onSortOptionChanged = {},
            onNavigateToPlaylist = {},
            onNavigateToAlbumClick = {},
            currentSortOption = SortOption.RECENTLY_ADDED,
            onAlbumClick = {},
            onPlaylistClick = {}
        )
    }
}