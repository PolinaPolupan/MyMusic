package com.example.mymusic.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.NetworkImage
import com.example.mymusic.core.designSystem.component.linearGradientScrim
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.core.designSystem.util.saturation
import com.example.mymusic.core.model.Artist

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedTrack(
    name: String,
    artists: List<Artist>,
    coverUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .size(
                dimensionResource(id = R.dimen.top_picks_card_min_size)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NetworkImage(
                imageUrl = coverUrl,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .linearGradientScrim(
                        color = MaterialTheme.colorScheme.primary
                            .saturation(2f)
                            .darker(0.5f),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 500f)
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineSmall.copy(lineHeight = 24.sp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = artistsString(artists),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview
@Composable
fun FeaturedTrackPreview() {
    MyMusicTheme {
        val mockTrack = PreviewParameterData.tracks[0]
        FeaturedTrack(
            name = mockTrack.name,
            artists = PreviewParameterData.artists,
            coverUrl = mockTrack.album.imageUrl,
            onClick = { /*TODO*/ }
        )
    }
}

@Preview
@Composable
fun FeaturedTrackLongNamePreview() {
    MyMusicTheme {
        val mockTrack = PreviewParameterData.tracks[0]
        FeaturedTrack(
            name = "This is a very very very very long name",
            artists = PreviewParameterData.artists,
            coverUrl = mockTrack.album.imageUrl,
            onClick = { /*TODO*/ }
        )
    }
}