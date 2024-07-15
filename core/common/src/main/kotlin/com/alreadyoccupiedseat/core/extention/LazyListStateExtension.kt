package com.alreadyoccupiedseat.core.extention

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousItemIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    var scrollingUp by remember(this) { mutableStateOf(true) }

    return remember(this) {
        derivedStateOf {
            if (previousItemIndex == firstVisibleItemIndex) {
                scrollingUp = firstVisibleItemScrollOffset - previousScrollOffset <= 0
            } else {
                previousItemIndex = firstVisibleItemIndex
            }

            previousScrollOffset = firstVisibleItemScrollOffset

            scrollingUp
        }
    }.value
}