package com.example.mymusic.core.designSystem.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.theme.MyMusicTheme

@Composable
internal fun SquareRoundedCornerPlaceholder(
    size: Dp,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse)
    )

    Box(
        modifier = modifier
            .border(BorderStroke(1.dp, color = Color.White.copy(alpha = 0.2f)), shape = RoundedCornerShape(24.dp))
            .clip(shape = RoundedCornerShape(24.dp))
            .size(size)
            .alpha(alpha)
            .background(color = Color.White)
    )
}

@Composable
internal fun SquarePlaceholder(
    size: Dp,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse)
    )

    Box(
        modifier = modifier
            .border(BorderStroke(1.dp, color = Color.White.copy(alpha = 0.2f)))
            .size(size)
            .alpha(alpha)
            .background(color = Color.White)
    )
}

@Composable
internal fun CirclePlaceholder(
    radius: Dp,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse)
    )

    Box(
        modifier = modifier
            .border(BorderStroke(1.dp, color = Color.White.copy(alpha = 0.2f)), shape = RoundedCornerShape(radius))
            .clip(shape = RoundedCornerShape(radius))
            .size(radius * 2)
            .alpha(alpha)
            .background(color = Color.White)
    )
}

@Preview
@Composable
fun SquareRoundedPlaceholderPreview() {
    MyMusicTheme {
        SquareRoundedCornerPlaceholder(size = dimensionResource(id = R.dimen.top_picks_card_min_size))
    }
}

@Preview
@Composable
fun SquarePlaceholderPreview() {
    MyMusicTheme {
        SquarePlaceholder(size = dimensionResource(id = R.dimen.top_picks_card_min_size))
    }
}

@Preview
@Composable
fun CirclePlaceholderPreview() {
    MyMusicTheme {
        CirclePlaceholder(radius = 100.dp)
    }
}