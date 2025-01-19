package com.fabsantosdev.pullhub.presentation.screens.repodetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabsantosdev.pullhub.R
import com.fabsantosdev.pullhub.domain.model.PullRequestQuery
import com.fabsantosdev.pullhub.presentation.common.components.layout.BaseScreenLayout
import com.fabsantosdev.pullhub.presentation.common.components.banner.BannerMessage
import com.fabsantosdev.pullhub.presentation.state.HandleUiState

@Composable
fun PullRequestScreen(
    modifier: Modifier = Modifier,
    query: PullRequestQuery,
    title: String = "",
    viewModel: PullRequestViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val listState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsState()
    val pullRequests by viewModel.allPullRequests.collectAsState()

    LaunchedEffect(query.ownerName, query.repositoryTitle) {
        viewModel.setPullRequestQuery(query)
    }

    BaseScreenLayout(
        modifier = modifier,
        topBar = {
            RepositoryListTopBar(
                title = title, onBackNavigate = onBackPressed
            )
        }
    ) {
        HandleUiState(
            uiState = uiState,
            data = pullRequests,
            onLoading = {
                if (pullRequests.isNotEmpty()) {
                    PullRequestList(pullRequests = pullRequests,
                        isLoading = true,
                        listState = listState,
                        onLoadMore = { viewModel.loadMoreRepositories() })
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }, onSuccess = {
                PullRequestList(
                    pullRequests = pullRequests,
                    isLoading = false,
                    listState = listState,
                    onLoadMore = { viewModel.loadMoreRepositories() }
                )
            }, onFailure = { errorMessage ->
                if (errorMessage == R.string.rate_limit_exceeded_please_try_again_later && pullRequests.isNotEmpty()) {
                    PullRequestList(
                        pullRequests = pullRequests,
                        isLoading = false,
                        listState = listState,
                        onLoadMore = { viewModel.loadMoreRepositories() }
                    )
                    BannerMessage(stringResource(errorMessage), isInfo = true)

                } else {
                    BannerMessage(stringResource(errorMessage), isInfo = false)
                }
            }, onEmpty = {
                if (pullRequests.isNotEmpty()) {
                    PullRequestList(pullRequests = pullRequests,
                        isLoading = false,
                        listState = listState,
                        onLoadMore = { viewModel.loadMoreRepositories() })

                    BannerMessage("Não há pull requests")

                } else {
                    BannerMessage("Não há pull requests")
                }
            })
    }
}

