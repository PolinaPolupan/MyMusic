package com.example.mymusic.ui.library

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.data.Playlist
import com.example.mymusic.designSystem.component.ClippedShadowCard
import com.example.mymusic.designSystem.component.MyMusicGradientBackground
import com.example.mymusic.designSystem.component.ScreenHeader
import com.example.mymusic.designSystem.component.Sort
import com.example.mymusic.designSystem.component.SortBottomSheet
import com.example.mymusic.designSystem.component.SortOption
import com.example.mymusic.designSystem.theme.MyMusicTheme

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
                PlaylistCard(name = playlist.name, owner = playlist.owner, coverRes = playlist.coverRes, onClick = {})
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaylistCard(
    name: String,
    owner: String,
    @DrawableRes coverRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
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
            Image(
                painter = painterResource(id = coverRes),
                contentDescription = null,
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
            coverRes = R.drawable.images,
            onClick = {}
        )
    }
}


@Preview
@Composable
fun LibraryPreview() {
    MyMusicTheme {
        LibraryContent(
            playlists = listOf(Playlist(R.drawable.images, "Dua Lipa", "Polina Polupan")),
            currentSortOption = SortOption.RECENTLY_ADDED,
            onSortOptionChanged = {}
        )
    }
}