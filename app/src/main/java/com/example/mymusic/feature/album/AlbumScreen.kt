package com.example.mymusic.feature.album

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.mymusic.core.designSystem.component.MyMusicGradientBackground
import com.example.mymusic.core.designSystem.component.NetworkImage
import com.example.mymusic.core.designSystem.component.linearGradientScrim
import com.example.mymusic.core.designSystem.theme.DynamicThemePrimaryColorsFromImage
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.theme.rememberDominantColorState
import com.example.mymusic.core.designSystem.util.contrastAgainst
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.core.ui.PreviewParameterData

@Composable
fun AlbumScreen(
    modifier: Modifier = Modifier,
    viewModel: AlbumViewModel = AlbumViewModel()
) {
    AlbumScreenContent(
        name = viewModel.currentAlbum.name,
        imageUrl = viewModel.currentAlbum.imageUrl,
        modifier = modifier
    )
}

@Composable
fun AlbumScreenContent(
    name: String,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= 3f
    }
    DynamicThemePrimaryColorsFromImage {
        // When the selected image url changes, call updateColorsFromImageUrl() or reset()
        LaunchedEffect(imageUrl) {
            dominantColorState.updateColorsFromImageUrl(imageUrl)
        }
        Box(modifier = modifier.fillMaxSize()) {
            NetworkImage(
                imageUrl = imageUrl,
                modifier = Modifier.size(400.dp)
            )
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .linearGradientScrim(
                        color = MaterialTheme.colorScheme.primary.darker(0.9f),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 1000f),
                        decay = 1f
                    )
            )
            Column {
                Spacer(modifier = Modifier.height(300.dp))
                Text(text = name, style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

@Preview
@Composable
fun AlbumScreenPreview(
    modifier: Modifier = Modifier
) {
    MyMusicTheme {
        val mockAlbum = PreviewParameterData.albums[0]
        AlbumScreenContent(
            name = mockAlbum.name,
            imageUrl = mockAlbum.imageUrl
        )
    }
}