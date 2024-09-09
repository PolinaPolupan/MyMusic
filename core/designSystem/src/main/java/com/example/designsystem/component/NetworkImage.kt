package com.example.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.designsystem.R
import com.example.designsystem.theme.MyMusicTheme


@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1f,
    defaultImageRes: Int = R.drawable.spotify_logo_white_on_green
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )
    val isLocalInspection = LocalInspectionMode.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            // Display a progress bar while loading
            DotsFlashing(modifier = Modifier.fillMaxSize().padding(32.dp))
        }

        Image(
            alpha = alpha,
            modifier = Modifier
                .fillMaxSize()
                .testTag(imageUrl ?: ""),
            contentScale = contentScale,
            painter = if (isError.not() && !isLocalInspection) {
                imageLoader
            } else {
                painterResource(defaultImageRes)
            },
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun NetworkImagePreview() {
    MyMusicTheme {
        NetworkImage(
            imageUrl = "https://www.followlyrics.com/storage/249/2480780.jpg"
        )
    }
}