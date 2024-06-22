package com.example.mymusic.core.designSystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.core.designSystem.theme.MyMusicTheme

@Composable
fun BlurredImageHeader(
    imageUrl: String,
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
) {
    Box(modifier = modifier.blur(50.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)) {
        AnimatedContent(
            targetState = imageUrl,
            label = "",
            transitionSpec = {
                fadeIn(animationSpec = tween(300, 0, easing = EaseIn)) togetherWith
                        fadeOut(animationSpec = tween(300, 0, EaseOut))

            }
        ) {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.TopCenter
            ) {
                NetworkImage(
                    imageUrl = it,
                    modifier = Modifier
                        .height(250.dp),
                    alpha = alpha

                )
            }
        }
    }
}

@Preview
@Composable
fun BlurredImagePreview() {
    MyMusicTheme {
        BlurredImageHeader(imageUrl = "https://i.scdn.co/image/ab67616d0000b273dd0a40eecd4b13e4c59988da")
    }
}