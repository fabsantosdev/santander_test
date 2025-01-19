package com.fabsantosdev.pullhub.presentation.screens.repodetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fabsantosdev.pullhub.domain.model.PullRequest
import com.fabsantosdev.pullhub.presentation.state.InfiniteScrollHandler
import com.fabsantosdev.pullhub.presentation.common.components.pullrequest.PullRequestCard
import com.fabsantosdev.pullhub.presentation.common.components.repository.RepositoryCardShimmerEffect

@Composable
fun PullRequestList(
    pullRequests: List<PullRequest>,
    isLoading: Boolean,
    listState: LazyListState,
    onLoadMore: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(vertical = 15.dp),
        state = listState
    ) {
        itemsIndexed(pullRequests) { index, pr ->
            PullRequestCard(
                title = pr.title,
                description = pr.description,
                username = pr.username,
                createdAt = pr.createdAt,
                pullRequestState = pr.state.displayName,
                imageUrl = pr.userAvatarUrl
            ) {}

            if (index < pullRequests.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
            }

        }

        if (isLoading) {
            item(key = "loading_header") {
                Spacer(modifier = Modifier.height(0.dp))
            }

            items(5, key = { "shimmer_$it" }) {
                RepositoryCardShimmerEffect()

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
            }
        }
    }

    InfiniteScrollHandler(
        listState = listState,
        itemCount = pullRequests.size,
        isLoading = isLoading,
        onLoadMore = onLoadMore
    )
}

