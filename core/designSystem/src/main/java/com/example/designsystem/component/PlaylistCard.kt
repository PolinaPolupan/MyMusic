package com.example.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.designsystem.R
import com.example.designsystem.theme.MyMusicTheme


@Composable
fun PlaylistCard(
    name: String,
    ownerName: String,
    imageUrl: String,
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
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            NetworkImage(
                imageUrl = imageUrl,
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
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                )
                Text(
                    text = stringResource(id = R.string.playlist_label, ownerName),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.alpha(0.65f)
                )
            }
            if (isSelected && isSelectable) {
                Icon(
                    imageVector = MyMusicIcons.Check,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(id = R.string.is_checked),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@PreviewWithBackground
@Composable
fun PlaylistCardPreview() {
    MyMusicTheme {
        val mockPlaylist = PreviewParameterData.playlists[0]
        PlaylistCard(
            name = mockPlaylist.name,
            ownerName = mockPlaylist.ownerName,
            imageUrl = mockPlaylist.imageUrl,
            onClick = { /*TODO*/ }
        )
    }
}

@PreviewWithBackground
@Composable
fun PlaylistCardLongNamePreview() {
    MyMusicTheme {
        val mockPlaylist = PreviewParameterData.playlists[0]
        PlaylistCard(
            name = "This is a very very very very long name",
            ownerName = mockPlaylist.ownerName,
            imageUrl = mockPlaylist.imageUrl,
            onClick = { /*TODO*/ }
        )
    }
}

@PreviewWithBackground
@Composable
fun PlaylistCardLongOwnerNamePreview() {
    MyMusicTheme {
        val mockPlaylist = PreviewParameterData.playlists[0]
        PlaylistCard(
            name = "This is a very very very very long name",
            ownerName = "This is a very very very very long owner name",
            imageUrl = mockPlaylist.imageUrl,
            onClick = { /*TODO*/ }
        )
    }
}

@PreviewWithBackground
@Composable
fun SelectablePlaylistCardPreview() {
    MyMusicTheme {
        val mockPlaylist = PreviewParameterData.playlists[0]
        PlaylistCard(
            name = "This is a very very very very long name",
            ownerName = mockPlaylist.ownerName,
            imageUrl = mockPlaylist.imageUrl,
            onClick = { /*TODO*/ },
            isSelected = true,
            isSelectable = true
        )
    }
}