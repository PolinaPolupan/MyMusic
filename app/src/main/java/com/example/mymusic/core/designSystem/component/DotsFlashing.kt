package com.example.mymusic.core.designSystem.component

import androidx.annotation.FloatRange
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DotsFlashing(
    dotSize: Dp = 24.dp,
    delayUnit: Int = 600,
    @FloatRange(from = 0.0, to = 1.0) minAlpha: Float = 0.3f,
    @FloatRange(from = 0.0, to = 1.0) maxAlpha: Float = 0.7f,
    color: Color = MaterialTheme.colorScheme.primary
) {

    @Composable
    fun Dot(
        alpha: Float
    ) = Spacer(
        Modifier
            .size(dotSize)
            .alpha(alpha)
            .background(
                color = color,
                shape = CircleShape
            )
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

    val alpha1 by animateAlphaWithDelay(0)
    val alpha2 by animateAlphaWithDelay(delayUnit)
    val alpha3 by animateAlphaWithDelay(delayUnit * 2)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val spaceSize = 2.dp

        Dot(alpha1)
        Spacer(Modifier.width(spaceSize))
        Dot(alpha2)
        Spacer(Modifier.width(spaceSize))
        Dot(alpha3)
    }
}

@Preview(showBackground = true)
@Composable
fun DotsPreview() = MaterialTheme {
    Column(modifier = Modifier.padding(4.dp)) {

        Text(
            text = "Dots flashing",
            style = MaterialTheme.typography.titleLarge
        )
        DotsFlashing()
    }
}