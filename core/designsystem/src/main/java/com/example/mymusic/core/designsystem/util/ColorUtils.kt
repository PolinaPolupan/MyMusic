package com.example.mymusic.core.designsystem.util

import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import kotlin.math.max
import kotlin.math.min

fun Color.contrastAgainst(background: Color): Float {
    val fg = if (alpha < 1f) compositeOver(background) else this

    val fgLuminance = fg.luminance() + 0.05f
    val bgLuminance = background.luminance() + 0.05f

    return max(fgLuminance, bgLuminance) / min(fgLuminance, bgLuminance)
}

fun Color.lighter(@FloatRange(from = 0.0, to = 1.0) factor: Float = 1f) =
    Color(ColorUtils.blendARGB(this.toArgb(), Color.White.toArgb(), factor))

fun Color.darker(@FloatRange(from = 0.0, to = 1.0) factor: Float = 1f) =
    Color(ColorUtils.blendARGB(this.toArgb(), Color.Black.toArgb(), factor))

fun Color.saturation(factor: Float = 1f): Color {
    val res = FloatArray(3)
    ColorUtils.colorToHSL(this.toArgb(), res)
    res[1] *= factor
    return Color(ColorUtils.HSLToColor(res))
}
