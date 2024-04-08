package com.example.mymusic.core.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
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
    val offset = remember { mutableStateOf(0) }

    LaunchedEffect(itemIndex.value, itemOffset.value) {
        // When we haven`t started scrolling yet
        if (previousItemIndex  == null || itemIndex.value == 0) {
            offset.value = itemOffset.value
        } else if (previousItemIndex  == itemIndex.value) {
            offset.value += (itemOffset.value - (previousItemOffset ?: 0))
        } else if (previousItemIndex  > itemIndex.value) {
            offset.value -= (previousItemOffset ?: 0)
        } else { // lastPosition.value < position.value
            offset.value += itemOffset.value
        }
    }

    return offset
}
