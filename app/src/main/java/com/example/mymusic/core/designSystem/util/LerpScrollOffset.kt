package com.example.mymusic.core.designSystem.util

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.ui.unit.fontscaling.MathUtils
import kotlin.math.min

/*
* Interpolates a given scroll offset between 0.0f and 1.0f
*/
@SuppressLint("RestrictedApi")
fun lerpScrollOffset(
    scrollState: State<Int>,
    valueMin: Float,
    valueMax: Float,
    reverse: Boolean = false
): Float {
    val value = if (scrollState.value >= valueMin) MathUtils.constrainedMap(
        0.0f,
        1.0f,
        valueMin,
        valueMax,
        scrollState.value.toFloat()
    ) else 0.0f
    return  if (reverse)
        1 - min(1.0f, value )
        else min(1.0f, value )
}