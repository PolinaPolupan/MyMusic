package com.example.mymusic.designSystem.component

import androidx.annotation.FloatRange
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CroppedShape(
    @FloatRange(from = 0.0, to = 1.0) private val widthPart: Float = 1f,
    @FloatRange(from = 0.0, to = 1.0) val heightPart: Float = 1f,
    private val reverseHeight: Boolean = false,
    private val reverseWidth: Boolean = false
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        var offsetX = 0f
        var offsetY = 0f
        if (reverseHeight) {
            offsetY = size.height * (1 - heightPart)
        }
        if (reverseWidth) {
            offsetX = size.width * (1 - widthPart)
        }
        return Outline.Rectangle(
            Rect(
                Offset(offsetX, offsetY),
                Size(size.width * widthPart, size.height * heightPart)
            )
        )
    }
}