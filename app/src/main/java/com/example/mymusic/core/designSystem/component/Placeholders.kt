package com.example.mymusic.core.designSystem.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.theme.MyMusicTheme

@Composable
internal fun RectangleRoundedCornerPlaceholder(
    size: Dp,
    modifier: Modifier = Modifier,
    cornerSize: Dp = 24.dp,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse)
    )

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(cornerSize))
            .size(size)
            .background(color = Color.White.copy(alpha = alpha))
            .shimmerLoadingAnimation()
    )
}

@Composable
internal fun RectangleRoundedCornerPlaceholder(
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier,
    cornerSize: Dp = 24.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse)
    )

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(cornerSize))
            .width(width)
            .height(height)
            .background(color = Color.White.copy(alpha = alpha))
            .shimmerLoadingAnimation()
    )
}


@Composable
internal fun RectanglePlaceholder(
    size: Dp,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse)
    )

    Box(
        modifier = modifier
            .size(size)
            .background(color = Color.White.copy(alpha = alpha))
            .shimmerLoadingAnimation()
    )
}

@Composable
internal fun RectanglePlaceholder(
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse)
    )

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .background(color = Color.White.copy(alpha = alpha))
            .shimmerLoadingAnimation()
    )
}

@Composable
internal fun CirclePlaceholder(
    radius: Dp,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse)
    )

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(radius))
            .size(radius * 2)
            .background(color = Color.White.copy(alpha = alpha))
            .shimmerLoadingAnimation()
    )
}

@Preview
@Composable
fun SquareRoundedPlaceholderPreview() {
    MyMusicTheme {
        RectangleRoundedCornerPlaceholder(size = dimensionResource(id = R.dimen.top_picks_card_min_size))
    }
}

@Preview
@Composable
fun SquarePlaceholderPreview() {
    MyMusicTheme {
        RectanglePlaceholder(size = dimensionResource(id = R.dimen.top_picks_card_min_size))
    }
}

@Preview
@Composable
fun CirclePlaceholderPreview() {
    MyMusicTheme {
        CirclePlaceholder(radius = 100.dp)
    }
}