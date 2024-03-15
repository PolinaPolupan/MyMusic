/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mymusic.core.designSystem.util

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

fun Color.lighter(factor: Float = 1f) =
    Color(ColorUtils.blendARGB(this.toArgb(), Color.White.toArgb(), factor))

fun Color.darker(factor: Float = 1f) =
    Color(ColorUtils.blendARGB(this.toArgb(), Color.Black.toArgb(), factor))

fun Color.saturation(factor: Float = 1f): Color {
    val res: FloatArray = FloatArray(3)
    ColorUtils.colorToHSL(this.toArgb(), res)
    res[1] *= factor
    return Color(ColorUtils.HSLToColor(res))
}
