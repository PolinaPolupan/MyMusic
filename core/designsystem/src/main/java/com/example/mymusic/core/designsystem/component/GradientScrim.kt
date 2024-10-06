package com.example.mymusic.core.designsystem.component

import androidx.annotation.FloatRange
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow


private class LinearGradientModifier(
    var onDraw: DrawScope.() -> Unit
) : Modifier.Node(), DrawModifierNode {

    override fun ContentDrawScope.draw() {
        onDraw()
        drawContent()
    }
}

/**
 * Draws a vertical gradient scrim in the foreground.
 *
 * @param color The color of the gradient scrim.
 * @param startYPercentage The start y value, in percentage of the layout's height (0f to 1f)
 * @param endYPercentage The end y value, in percentage of the layout's height (0f to 1f). This
 * value can be smaller than [startYPercentage]. If that is the case, then the gradient direction
 * will reverse (decaying downwards, instead of decaying upwards).
 * @param decay The exponential decay to apply to the gradient. Defaults to `1.0f` which is
 * a linear gradient.
 * @param numStops The number of color stops to draw in the gradient. Higher numbers result in
 * the higher visual quality at the cost of draw performance. Defaults to `16`.
 */
fun Modifier.linearGradientScrim(
    color: Color,
    @FloatRange(from = 0.0, to = 1.0) startYPercentage: Float = 0f,
    @FloatRange(from = 0.0, to = 1.0) endYPercentage: Float = 1f,
    start: Offset = Offset(0.0f, 0.0f),
    end: Offset = Offset(0.0f, 500.0f),
    decay: Float = 1.0f,
    numStops: Int = 16
) = this then LinearGradientElement(color, startYPercentage, endYPercentage, start, end, decay, numStops)

private data class LinearGradientElement(
    var color: Color,
    var startYPercentage: Float = 0f,
    var endYPercentage: Float = 1f,
    var start: Offset = Offset(0.0f, 0.0f),
    var end: Offset = Offset(0.0f, 500.0f),
    var decay: Float = 1.0f,
    var numStops: Int = 16
) : ModifierNodeElement<LinearGradientModifier>() {
    fun createOnDraw(): DrawScope.() -> Unit {
        val colors = if (decay != 1f) {
            // If we have a non-linear decay, we need to create the color gradient steps
            // manually
            val baseAlpha = color.alpha
            List(numStops) { i ->
                val x = i * 1f / (numStops - 1)
                val opacity = x.pow(decay)
                color.copy(alpha = baseAlpha * opacity)
            }
        } else {
            // If we have a linear decay, we just create a simple list of start + end colors
            listOf(color.copy(alpha = 0f), color)
        }

        val brush =
            // Reverse the gradient if decaying downwards
            Brush.linearGradient(
                colors = if (startYPercentage < endYPercentage) colors else colors.reversed(),
                start = start,
                end = end
            )

        return {
            val topLeft = Offset(0f, size.height * min(startYPercentage, endYPercentage))
            val bottomRight =
                Offset(size.width, size.height * max(startYPercentage, endYPercentage))

            drawRect(
                topLeft = topLeft,
                size = Rect(topLeft, bottomRight).size,
                brush = brush
            )
        }
    }

    override fun create() = LinearGradientModifier(createOnDraw())

    override fun update(node: LinearGradientModifier) {
        node.onDraw = createOnDraw()
    }

    /**
     * Allow this custom modifier to be inspected in the layout inspector
     **/
    override fun InspectorInfo.inspectableProperties() {
        name = "verticalGradientScrim"
        properties["color"] = color
        properties["startYPercentage"] = startYPercentage
        properties["endYPercentage"] = endYPercentage
        properties["decay"] = decay
        properties["numStops"] = numStops
    }
}