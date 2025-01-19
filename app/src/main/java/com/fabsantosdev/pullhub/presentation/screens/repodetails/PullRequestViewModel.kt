package com.fabsantosdev.pullhub.presentation.screens.repodetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabsantosdev.pullhub.domain.model.PullRequest
import com.fabsantosdev.pullhub.domain.model.PullRequestQuery
import com.fabsantosdev.pullhub.domain.model.UiState
import com.fabsantosdev.pullhub.domain.usecase.GetPullRequestsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PullRequestViewModel @Inject constructor(
    private val getPullRequestsUseCase: GetPullRequestsUseCase
) : ViewModel() {

    private val _currentPage = MutableStateFlow(1)
    private val _allPullRequests = MutableStateFlow<List<PullRequest>>(emptyList())
    val allPullRequests: StateFlow<List<PullRequest>> = _allPullRequests.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState.asStateFlow()

    private val _pullRequestQuery = MutableStateFlow<PullRequestQuery?>(null)

    private var isLoadingMore = false

    fun setPullRequestQuery(pullRequestQuery: PullRequestQuery) {
        viewModelScope.launch(Dispatchers.IO) {
            _pullRequestQuery.value = pullRequestQuery
            _currentPage.value = 1
            fetchPullRequests(pullRequestQuery, newQuery = true)
        }
    }

    private suspend fun fetchPullRequests(
        pullRequestQuery: PullRequestQuery, newQuery: Boolean
    ) {
        if (newQuery) {
            cleanPullRequestList()
        }

        _uiState.value = UiState.Loading

        try {
            getPullRequestsUseCase.execute(pullRequestQuery).collect { uiState ->
                    when (uiState) {
                        is UiState.Success -> {
                            val updatedList = if (_currentPage.value == 1) {
                                uiState.data
                            } else {
                                _allPullRequests.value + uiState.data
                            }
                            _allPullRequests.value = updatedList
                            _uiState.value = UiState.Success(Unit)
                            isLoadingMore = false
                        }

                        is UiState.Loading -> {
                            _uiState.value = UiState.Loading
                        }

                        is UiState.Failure -> {
                            _uiState.value = UiState.Failure(
                                errorCode = uiState.errorCode, errorMessage = uiState.errorMessage
                            )
                            isLoadingMore = false
                        }

                        is UiState.Empty -> {
                            _uiState.value = UiState.Empty
                            isLoadingMore = false
                        }

                        is UiState.Idle -> {
                            _uiState.value = UiState.Idle
                            isLoadingMore = false
                        }
                    }
                }
        } catch (e: Exception) {
            _uiState.value = UiState.Failure(
                errorCode = null, errorMessage = "Erro ao buscar pull requests: ${e.message}"
            )
            isLoadingMore = false
        }
    }

    fun loadMoreRepositories() {
        if (isLoadingMore) return

        _pullRequestQuery.value?.let { currentQuery ->
            isLoadingMore = true
            _currentPage.value += 1

            val updatedQuery = currentQuery.copy(pageNumber = _currentPage.value)
            _pullRequestQuery.value = updatedQuery

            viewModelScope.launch(Dispatchers.IO) {
                fetchPullRequests(updatedQuery, newQuery = false)
            }
        }
    }

    private fun cleanPullRequestList() {
        _allPullRequests.value = emptyList()
    }
}
