package com.example.mymusic.core.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.core.designSystem.util.saturation

/**
 * The main background for the app.
 *
 * @param modifier Modifier to be applied to the background.
 * @param content The background content.
 */
@Composable
fun MyMusicGradientBackground(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.BottomCenter,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.tertiary
                    .darker(0.85f)
                    .saturation(3f)
            )
            .linearGradientScrim(
                color = Color.Black
                    .copy(alpha = 0.9f),
                startYPercentage = 0f,
                endYPercentage = 1f,
                start = Offset(0f, 0f),
                end = Offset(0f, 1500f),
                decay = 1f
            )
    ) {
        content()
    }
}

/**
 * [MyMusicHomeBackground] is used in [HomeScreen]
 *
 * @param modifier Modifier to be applied to the background.
 * @param content The background content.
 */
@Composable
fun MyMusicHomeBackground(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.BottomCenter,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.tertiary
                    .darker(0.9f)
                    .saturation(1f)
            )
    ) {
        content()
    }
}

/**
 * [MyMusicLoginBackground] is used in [LoginScreen]
 *
 * @param modifier Modifier to be applied to the background.
 * @param content The background content.
 */
@Composable
fun MyMusicLoginBackground(
    color: Color,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.BottomCenter,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
            .fillMaxSize()
            .background(
                color = color
            )
            .linearGradientScrim(
                color = Color.Black,
                end = Offset(0f, 2800f),
                decay = 0.6f
            )
    ) {
        content()
    }
}

@Preview
@Composable
fun MyMusicGradientBackgroundPreview() {
    MyMusicTheme {
        MyMusicGradientBackground {

        }
    }
}

@Preview
@Composable
fun MyMusicHomeBackgroundPreview() {
    MyMusicTheme {
        MyMusicHomeBackground {

        }
    }
}

@Preview
@Composable
fun MyMusicLoginBackgroundPreview() {
    MyMusicTheme {
        MyMusicLoginBackground(color = MaterialTheme.colorScheme.primary
            .saturation(6f)) {

        }
    }
}