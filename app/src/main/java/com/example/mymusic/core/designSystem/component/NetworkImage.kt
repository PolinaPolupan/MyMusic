package com.example.mymusic.core.designSystem.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.theme.MyMusicTheme

@Composable
fun NetworkImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.loading_img),
        error = painterResource(id = R.drawable.ic_broken_image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
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