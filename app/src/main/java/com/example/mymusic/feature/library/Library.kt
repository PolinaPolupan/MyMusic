package com.example.mymusic.feature.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.ClippedShadowCard
import com.example.mymusic.core.designSystem.component.MyMusicGradientBackground
import com.example.mymusic.core.designSystem.component.NetworkImage
import com.example.mymusic.core.designSystem.component.ScreenHeader
import com.example.mymusic.core.designSystem.component.Sort
import com.example.mymusic.core.designSystem.component.SortBottomSheet
import com.example.mymusic.core.designSystem.component.SortOption
import com.example.mymusic.core.designSystem.icon.MyMusicIcons
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.model.Playlist


@Composable
fun Library(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = LibraryViewModel()
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        LibraryContent(
            playlists = viewModel.usersPlaylists,
            currentSortOption = viewModel.currentSortOption.value,
            onSortOptionChanged =  { viewModel.currentSortOption.value = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryContent(
    playlists: List<Playlist>,
    onSortOptionChanged: (SortOption) -> Unit,
    currentSortOption: SortOption,
    modifier: Modifier = Modifier
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        SortBottomSheet(
            currentOption = currentSortOption,
            onDismissRequest = { showBottomSheet = false },
            onSelection = onSortOptionChanged
        )
    }

    MyMusicGradientBackground(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            ScreenHeader(
                titleRes = R.string.your_library,
                onAvatarClick = { /*TODO*/ },
                avatarImageRes = R.drawable.images
            )

            Sort(
                sortOption = currentSortOption,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(16.dp)
                    .clickable {
                        showBottomSheet = true
                    }
            )
            for (playlist in playlists) {
                PlaylistCard(name = playlist.name, owner = playlist.owner, coverUrl = playlist.coverUrl, onClick = {})
            }
        }
    }
}

@Composable
fun PlaylistCard(
    name: String,
    owner: String,
    coverUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelectable: Boolean = false,
    isSelected: Boolean = false
) {
    ClippedShadowCard(
        elevation = 8.dp,
        onClick = onClick,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.1f)),
        modifier = modifier
            .width(dimensionResource(id = R.dimen.player_card_width))
            .padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            NetworkImage(
                imageUrl = coverUrl,
                modifier = Modifier
                    .size(70.dp)
            )
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .weight(2f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                )
                Text(
                    text = stringResource(id = R.string.playlist_label, owner),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            if (isSelectable && isSelected) {
                Icon(
                    imageVector = MyMusicIcons.Check,
                    contentDescription = stringResource(id = R.string.is_checked),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}



@Preview
@Composable
fun PlaylistCardPreview() {
    MyMusicTheme {
        PlaylistCard(
            name = "Liked songs",
            owner = "Polina Polupan",
            coverUrl = "",
            onClick = {}
        )
    }
}


@Preview
@Composable
fun LibraryPreview() {
    MyMusicTheme {
        LibraryContent(
            playlists = listOf(Playlist("", "Dua Lipa", "Polina Polupan")),
            currentSortOption = SortOption.RECENTLY_ADDED,
            onSortOptionChanged = {}
        )
    }
}