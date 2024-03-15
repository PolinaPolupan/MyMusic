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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.Sort
import com.example.mymusic.core.designSystem.component.SortBottomSheet
import com.example.mymusic.core.designSystem.component.SortOption
import com.example.mymusic.core.designSystem.icon.MyMusicIcons
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.model.Playlist
import com.example.mymusic.feature.library.PlaylistCard

@Composable
fun AddToPlayList(
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddToPlaylistViewModel = AddToPlaylistViewModel()
) {
    AddToPlayListContent(
        playlists = viewModel.usersPlaylists,
        onSortOptionChanged = viewModel::setSortOption,
        currentSortOption = viewModel.currentSortOption.value,
        onBackPress = onBackPress,
        modifier = modifier
    )
}

@Composable
fun AddToPlayListContent(
    playlists: List<Playlist>,
    onBackPress: () -> Unit,
    onSortOptionChanged: (SortOption) -> Unit,
    currentSortOption: SortOption,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
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

@Composable
private fun TopAppBar(
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
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