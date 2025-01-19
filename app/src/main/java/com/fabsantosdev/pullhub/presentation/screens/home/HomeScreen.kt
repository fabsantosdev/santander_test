package com.fabsantosdev.pullhub.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabsantosdev.pullhub.R
import com.fabsantosdev.pullhub.presentation.common.components.layout.BaseScreenLayout
import com.fabsantosdev.pullhub.presentation.state.ErrorState
import com.fabsantosdev.pullhub.presentation.state.HandleUiState


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onDetailsClick: (userName: String, repositoryName: String) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val repositories by viewModel.repositories.collectAsState()
    val listState = rememberLazyListState()

    BaseScreenLayout(
        modifier = modifier,
        topBar = {
            HomeTopBar(title = stringResource(R.string.app_name))
        }
    ) {
        HandleUiState(
            uiState = uiState,
            data = repositories,
            onLoading = {
                if (repositories.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    RepositoryList(
                        repositories = repositories,
                        isLoading = true,
                        listState = listState,
                        onLoadMore = { viewModel.loadMoreRepositories() },
                        onDetailsClick = onDetailsClick
                    )
                }
            },
            onSuccess = {
                RepositoryList(
                    repositories = repositories,
                    isLoading = false,
                    listState = listState,
                    onLoadMore = { viewModel.loadMoreRepositories() },
                    onDetailsClick = onDetailsClick
                )
            },
            onFailure = { errorMessage ->
                if (repositories.isNotEmpty()) {
                    RepositoryList(
                        repositories = repositories,
                        isLoading = false,
                        listState = listState,
                        onLoadMore = { viewModel.loadMoreRepositories() },
                        onDetailsClick = onDetailsClick
                    )
                } else {
                    ErrorState(
                        errorMessage = stringResource(errorMessage),
                        onRetry = { viewModel.fetchRepositories() }
                    )
                }
            })
    }
}



