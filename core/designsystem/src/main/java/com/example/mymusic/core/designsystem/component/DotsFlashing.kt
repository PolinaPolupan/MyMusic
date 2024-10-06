package com.example.mymusic.core.designsystem.component

import androidx.annotation.FloatRange
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun DotsFlashing(
    modifier: Modifier = Modifier,
    count: Int = 3,
    delayUnit: Int = 300,
    @FloatRange(from = 0.0, to = 1.0) minAlpha: Float = 0.3f,
    @FloatRange(from = 0.0, to = 1.0) maxAlpha: Float = 0.7f
) {
    @Composable
    fun Dot(
        alpha: Float,
        modifier: Modifier = Modifier
    ) = Spacer(
        modifier
            .alpha(alpha)
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .aspectRatio(1f)
    )

    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")

    @Composable
    fun animateAlphaWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = minAlpha,
        targetValue = minAlpha,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                minAlpha at delay using LinearEasing
                maxAlpha at delay + delayUnit using LinearEasing
                minAlpha at delay + delayUnit * 2
            }
        ), label = ""
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        for (i in 0..<count) {
            val alpha by animateAlphaWithDelay(delayUnit * i)
            Dot(alpha, modifier = Modifier.fillMaxWidth().weight(3f))
            if (i != count - 1) Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DotsPreview() = MaterialTheme {
    DotsFlashing()
}