package com.example.mymusic.core.designsystem.util

import android.annotation.SuppressLint
import androidx.annotation.FloatRange
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.unit.fontscaling.MathUtils
import kotlin.math.min

/**
 * Interpolates a given scroll offset between 0.0f and 1.0f
 */
@SuppressLint("RestrictedApi")
fun lerpScrollOffset(
    scrollOffset: Int,
    valueMin: Float,
    valueMax: Float,
    @FloatRange(from = 0.0, to = 1.0) rangeMin: Float = 0f,
    @FloatRange(from = 0.0, to = 1.0) rangeMax: Float = 1f,
    reverse: Boolean = false
): Float {
    val value = if (scrollOffset >= valueMin) MathUtils.constrainedMap(
        rangeMin,
        rangeMax,
        valueMin,
        valueMax,
        scrollOffset.toFloat()
    ) else 0.0f
    return  if (reverse)
        1 - min(1.0f, value )
        else min(1.0f, value )
}