package com.example.mymusic.core.designSystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mymusic.core.designSystem.theme.MyMusicTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClippedShadowCard(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: Dp = 0.dp,
    border: BorderStroke? = null,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Layout(
        {
            ClippedShadow(
                elevation = elevation,
                shape = shape,
                modifier = modifier
            )
            Card(
                modifier = modifier,
                shape = shape,
                colors = colors,
                onClick = onClick,
                elevation = CardDefaults.cardElevation(0.dp),
                border = border,
                content = content
            )
        }
    ) {measurables, constraints ->
        require(measurables.size == 2)

        val shadow = measurables[0]
        val target = measurables[1]

        val targetPlaceable = target.measure(constraints)
        val width = targetPlaceable.width
        val height = targetPlaceable.height

        val shadowPlaceable = shadow.measure(Constraints.fixed(width, height))

        layout(width, height) {
            shadowPlaceable.place(0, 0)
            targetPlaceable.place(0, 0)
        }
    }
}

@Preview
@Composable
fun ClippedShadowCardPreview() {
    MyMusicTheme {
        ClippedShadowCard(
            elevation = 8.dp,
            modifier = Modifier.size(80.dp)
        ) {

        }
    }
}