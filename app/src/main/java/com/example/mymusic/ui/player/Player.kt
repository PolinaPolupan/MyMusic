package com.example.mymusic.ui.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.data.Track
import com.example.mymusic.designSystem.theme.MyMusicTheme
import com.example.mymusic.designSystem.util.linearGradientScrim

@Composable
fun Player(
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = PlayerViewModel()
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        PlayerContent(
            track = viewModel.playingTrack
        )
    }
}

@Composable
fun PlayerContent(
    track: Track,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.size(540.dp)) {
            Image(
                painter = painterResource(id = track.cover),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                //.blur(30.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .linearGradientScrim(
                    color = MaterialTheme.colorScheme.primary,
                    start = Offset(0f, 0f),
                    end = Offset(100f, 1400f),
                    decay = 1f
                )
        ) {

        }
    }
}

@Preview
@Composable
fun PlayerPreview() {
    MyMusicTheme {
        PlayerContent(track = Track(cover = R.drawable.images))
    }
}