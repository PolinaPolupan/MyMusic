package com.example.mymusic.ui.player

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.data.Track
import com.example.mymusic.designSystem.component.CroppedShape
import com.example.mymusic.designSystem.icon.MyMusicIcons
import com.example.mymusic.designSystem.theme.DynamicThemePrimaryColorsFromImage
import com.example.mymusic.designSystem.theme.MyMusicTheme
import com.example.mymusic.designSystem.theme.rememberDominantColorState
import com.example.mymusic.designSystem.util.contrastAgainst
import com.example.mymusic.designSystem.util.linearGradientScrim
import com.example.mymusic.designSystem.util.saturation
import java.time.Duration

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
    val surfaceColor = MaterialTheme.colorScheme.surface
    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= 3f
    }
    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        // When the selected image url changes, call updateColorsFromImageUrl() or reset()
        LaunchedEffect(track) {
            dominantColorState.updateColorsFromImageUrl(track.cover)
        }
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
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
                    .linearGradientScrim(
                        color = MaterialTheme.colorScheme.primary
                            .saturation(3f),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 1500f),
                        decay = 10f
                    )
            ) {}
            Image(
                painter = painterResource(id = track.cover),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(50.dp)
                    .align(Alignment.BottomCenter)
                    .clip(CroppedShape(heightPart = 0.4f, reverseHeight = true))
            )
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .linearGradientScrim(
                        color = MaterialTheme.colorScheme.primary
                            .saturation(2f)
                            .copy(alpha = 0.7f),
                        start = Offset(0f, 0f),
                        end = Offset(600f, 1400f),
                        decay = 2f
                    )
                    .linearGradientScrim(
                        color = Color.Black.copy(alpha = 0.6f),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 2500f),
                        decay = 3f
                    )
            ) {}
        }
    }
}

@Composable
private fun PlayerButtons(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 48.dp
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val buttonsModifier = Modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }

        Image(
            imageVector = MyMusicIcons.SkipPrevious,
            contentDescription = stringResource(R.string.skip_previous),
            contentScale = ContentScale.Fit,
            modifier = buttonsModifier
        )
        Image(
            imageVector = MyMusicIcons.Play,
            contentDescription = stringResource(R.string.play),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(playerButtonSize)
                .semantics { role = Role.Button }
        )
        Image(
            imageVector = MyMusicIcons.SkipNext,
            contentDescription = stringResource(R.string.skip_next),
            contentScale = ContentScale.Fit,
            modifier = buttonsModifier
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun PlayerSlider(episodeDuration: Duration?) {
    if (episodeDuration != null) {
        Column(Modifier.fillMaxWidth()) {
            Slider(value = 0f, onValueChange = { })
            Row(Modifier.fillMaxWidth()) {
                Text(text = "0s")
                Spacer(modifier = Modifier.weight(1f))
                Text("${episodeDuration.seconds}s")
            }
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PlayerSliderPreview() {
    MyMusicTheme {
        PlayerSlider(episodeDuration = Duration.ZERO)
    }
}

@Preview
@Composable
fun PlayerButtonsPreview() {
    MyMusicTheme {
        PlayerButtons()
    }
}