package com.example.mymusic.feature.addToPlaylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.BlurredImageHeader
import com.example.mymusic.core.designSystem.component.Sort
import com.example.mymusic.core.designSystem.component.SortBottomSheet
import com.example.mymusic.core.designSystem.component.SortOption
import com.example.mymusic.core.designSystem.component.linearGradientScrim
import com.example.mymusic.core.designSystem.icon.MyMusicIcons
import com.example.mymusic.core.designSystem.theme.DynamicThemePrimaryColorsFromImage
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.theme.rememberDominantColorState
import com.example.mymusic.core.designSystem.util.contrastAgainst
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.core.model.Playlist
import com.example.mymusic.core.model.Track
import com.example.mymusic.core.ui.viewModelProviderFactoryOf
import com.example.mymusic.feature.library.PlaylistCard

@Composable
fun AddToPlayList(
    trackId: String,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: AddToPlaylistViewModel = viewModel(
        factory = viewModelProviderFactoryOf { AddToPlaylistViewModel(trackId) }
    )
    AddToPlayListContent(
        track = viewModel.currentTrack,
        playlists = viewModel.usersPlaylists,
        onSortOptionChanged = viewModel::setSortOption,
        currentSortOption = viewModel.currentSortOption.value,
        onBackPress = onBackPress,
        modifier = modifier
    )
}

@Composable
fun AddToPlayListContent(
    track: Track,
    playlists: List<Playlist>,
    onBackPress: () -> Unit,
    onSortOptionChanged: (SortOption) -> Unit,
    currentSortOption: SortOption,
    modifier: Modifier = Modifier
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= 3f
    }
    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        // When the selected image url changes, call updateColorsFromImageUrl() or reset()
        LaunchedEffect(track.imageUrl) {
            dominantColorState.updateColorsFromImageUrl(track.imageUrl)
        }
        BlurredImageHeader(imageUrl = track.imageUrl)
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
                    onSelection = onSortOptionChanged
                )
            }
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(onBackPress = onBackPress)
                Sort(
                    sortOption = currentSortOption,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(16.dp)
                        .clickable {
                            showBottomSheet = true
                        }
                )
                playlists.forEach {
                    var isSelected by remember {
                        mutableStateOf(false)
                    }
                    PlaylistCard(
                        name = it.name,
                        owner = it.owner,
                        coverUrl = it.coverUrl,
                        onClick = { isSelected = !isSelected },
                        isSelectable = true,
                        isSelected = isSelected,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)

                    )
                }
            }
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

@Composable
private fun TopAppBar(
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
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
            Text(
                text = stringResource(id = R.string.add_to_playlist),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize()
            )
        }
    }
}

@Preview
@Composable
fun AddToPlayListPreview(modifier: Modifier = Modifier) {
    MyMusicTheme {
        AddToPlayListContent(
            track = Track(
                id = "1",
                imageUrl = "https://images.genius.com/c05b3c4739a994bca85d932f6d6cb586.1000x1000x1.png",
                name = "Sugar",
                artist = "Maroon 5"
            ),
            playlists = listOf(Playlist("", "Dua Lipa", "Polina Polupan")),
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