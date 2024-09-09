package com.example.designsystem.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.MyMusicTheme
import kotlin.math.pow
import kotlin.math.sqrt

fun Modifier.shimmerLoadingAnimation(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
    numStops: Int = 32
): Modifier {
    return composed {

        val start = -1 * numStops / 2

        val colors = mutableListOf<Color>()

        val sigma = 10f
        val mu = 0f

        for (x in start..numStops / 2) {
            val alpha = 1 / sqrt(2 * Math.PI * sigma.pow(2)) * Math.E.pow((-1 * (x - mu).pow(2) / (2 * sigma.pow(2))).toDouble())
            val color = Color.White.copy(alpha.toFloat())
            colors.add(color)
        }

        val transition = rememberInfiniteTransition(label = "")

        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )

        this.background(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
        )
    }
}

@Preview
@Composable
fun ShimmerPreview() {
    MyMusicTheme {
        RectangleRoundedCornerPlaceholder(size = 300.dp)
    }
}