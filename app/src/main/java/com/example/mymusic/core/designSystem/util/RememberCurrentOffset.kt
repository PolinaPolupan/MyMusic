package com.example.mymusic.core.designSystem.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

/**
 * Returns the scroll Y position value in LazyColumn
 */
@Composable
fun rememberScrollState(state: LazyListState): MutableState<Int> {
    // Save the current offset and position
    val itemIndex = remember { derivedStateOf { state.firstVisibleItemIndex } }
    val itemOffset = remember { derivedStateOf { state.firstVisibleItemScrollOffset } }
    // Retrieve the previous position and offset
    val previousItemIndex = rememberPrevious(itemIndex.value)
    val previousItemOffset = rememberPrevious(itemOffset.value)
    // The resulting offset
    val offset = remember { mutableIntStateOf(0) }

    LaunchedEffect(itemIndex.value, itemOffset.value) {
        // When we haven`t started scrolling yet
        if (previousItemIndex  == null || itemIndex.value == 0) {
            offset.intValue = itemOffset.value
        } else if (previousItemIndex  == itemIndex.value) {
            offset.intValue += (itemOffset.value - (previousItemOffset ?: 0))
        } else if (previousItemIndex  > itemIndex.value) {
            offset.intValue -= (previousItemOffset ?: 0)
        } else { // lastPosition.value < position.value
            offset.intValue += itemOffset.value
        }
    }

    return offset
}
