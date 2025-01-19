package com.fabsantosdev.pullhub.domain.usecase

import android.util.Log
import com.fabsantosdev.pullhub.R
import com.fabsantosdev.pullhub.data.mapper.PullRequestMapper
import com.fabsantosdev.pullhub.domain.model.PullRequest
import com.fabsantosdev.pullhub.domain.model.PullRequestQuery
import com.fabsantosdev.pullhub.domain.model.UiState
import com.fabsantosdev.pullhub.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPullRequestsUseCaseImpl @Inject constructor(
    private val repository: GithubRepository,
    private val pullRequestMapper: PullRequestMapper
) : GetPullRequestsUseCase {

    override suspend fun execute(params: PullRequestQuery): Flow<UiState<List<PullRequest>>> {
        return repository.getPullRequests(params)
            .map { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        val pullRequests = uiState.data.map(pullRequestMapper::mapToDomain)
                        if (pullRequests.isEmpty()) UiState.Empty
                        else UiState.Success(pullRequests)
                    }
                    is UiState.Failure -> uiState
                    is UiState.Loading -> uiState
                    is UiState.Empty -> uiState
                    is UiState.Idle -> uiState
                }
            }
            .catch { throwable ->
                Log.e("GetPullRequestsUseCase", "Erro ao buscar pull requests", throwable)
                emit(
                    UiState.Failure(
                        errorCode = R.string.an_unexpected_error_occurred,
                        errorMessage = null
                    )
                )
            }
    }
}