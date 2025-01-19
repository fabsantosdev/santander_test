package com.fabsantosdev.pullhub.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fabsantosdev.pullhub.domain.model.Repo
import com.fabsantosdev.pullhub.presentation.state.InfiniteScrollHandler
import com.fabsantosdev.pullhub.presentation.common.components.repository.RepositoryCard
import com.fabsantosdev.pullhub.presentation.common.components.repository.RepositoryCardShimmerEffect

@Composable
fun RepositoryList(
    repositories: List<Repo>,
    isLoading: Boolean,
    listState: LazyListState,
    onLoadMore: () -> Unit,
    onDetailsClick: (userName: String, repositoryName: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(vertical = 15.dp),
        state = listState
    ) {
        items(repositories) { repo ->
            RepositoryCard(
                id = repo.id,
                repositoryName = repo.repositoryName,
                repositoryDescription = repo.repositoryDescription,
                starsCount = repo.starsCount,
                forksCount = repo.forkCount,
                imageUrl = repo.imageUrl,
                username = repo.username,
            ) {
                onDetailsClick(repo.username, repo.repositoryName)
            }
        }

        if (isLoading) {
            item(key = "loading_header") {
                Spacer(modifier = Modifier.height(0.dp))
            }

            items(5, key = { "shimmer_$it" }) {
                RepositoryCardShimmerEffect()
            }
        }
    }

    InfiniteScrollHandler(
        listState = listState,
        itemCount = repositories.size,
        isLoading = isLoading,
        onLoadMore = onLoadMore
    )
}