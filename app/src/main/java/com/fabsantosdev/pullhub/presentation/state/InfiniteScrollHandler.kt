package com.fabsantosdev.pullhub.presentation.state

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow

@Composable
fun InfiniteScrollHandler(
    listState: LazyListState,
    itemCount: Int,
    isLoading: Boolean,
    onLoadMore: () -> Unit
) {
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == itemCount - 1 && !isLoading) {
                    onLoadMore()
                }
            }
    }
}