package com.fabsantosdev.pullhub.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabsantosdev.pullhub.domain.model.Repo
import com.fabsantosdev.pullhub.domain.model.UiState
import com.fabsantosdev.pullhub.domain.usecase.GetRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRepositoriesUseCase: GetRepositoriesUseCase
) : ViewModel() {

    private val _currentPage = MutableStateFlow(1)

    private val _repositories = MutableStateFlow<List<Repo>>(emptyList())
    val repositories: StateFlow<List<Repo>> = _repositories

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    private var isLoadingMore = false

    init {
        fetchRepositories()
    }

    fun fetchRepositories() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getRepositoriesUseCase.execute(params = GetRepositoriesUseCase.Params(_currentPage.value)).collect { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        _repositories.value += uiState.data
                        _uiState.value = UiState.Success(Unit)
                        isLoadingMore = false
                    }
                    is UiState.Failure -> {
                        _uiState.value = UiState.Failure(errorCode = uiState.errorCode, uiState.errorMessage)
                        isLoadingMore = false
                    }
                    else -> Unit
                }
            }
        }
    }

    fun loadMoreRepositories() {
        if (isLoadingMore) return
        isLoadingMore = true
        _currentPage.value += 1
        fetchRepositories()
    }
}

