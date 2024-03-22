package com.example.mymusic.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.NetworkImage
import com.example.mymusic.core.designSystem.component.linearGradientScrim
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.model.Artist

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackCard(
    name: String,
    artists: List<Artist>,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        shape = RoundedCornerShape(2.dp),
        modifier = modifier
            .size(dimensionResource(id = R.dimen.track_card_size))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NetworkImage(
                imageUrl = imageUrl,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .linearGradientScrim(
                        color = Color.Black,
                        start = Offset(0f, 0f),
                        end = Offset(0f, 500f)
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(lineHeight = 16.sp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                var artistsString = ""
                for (artist in artists) {
                    artistsString += artist.name + ", "
                }
                artistsString = artistsString.substring(0, artistsString.length - 2)
                Text(
                    text = artistsString,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun TrackCardPreview() {
    MyMusicTheme {
        val mockTrack = PreviewParameterData.tracks[0]
        TrackCard(
            name = mockTrack.name,
            artists = PreviewParameterData.artists,
            imageUrl = mockTrack.imageUrl,
            onClick = { /*TODO*/ }
        )
    }
}

@Preview
@Composable
fun TrackCardLongNamePreview() {
    MyMusicTheme {
        val mockTrack = PreviewParameterData.tracks[0]
        TrackCard(
            name = "This is a very very very very long name",
            artists = PreviewParameterData.artists,
            imageUrl = mockTrack.imageUrl,
            onClick = { /*TODO*/ }
        )
    }
}