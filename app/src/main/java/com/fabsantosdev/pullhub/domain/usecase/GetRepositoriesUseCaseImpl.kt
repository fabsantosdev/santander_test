package com.fabsantosdev.pullhub.domain.usecase

import android.util.Log
import com.fabsantosdev.pullhub.R
import com.fabsantosdev.pullhub.data.mapper.RepoMapper
import com.fabsantosdev.pullhub.domain.model.Repo
import com.fabsantosdev.pullhub.domain.model.UiState
import com.fabsantosdev.pullhub.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRepositoriesUseCaseImpl @Inject constructor(
    private val repository: GithubRepository,
    private val repoMapper: RepoMapper
) : GetRepositoriesUseCase {

    override suspend fun execute(params: GetRepositoriesUseCase.Params): Flow<UiState<List<Repo>>> {
        return repository.getRepositories(
            pageNumber = params.pageNumber,
            language = params.language,
            itemsPerPage = params.itemsPerPage
        )
            .map { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        val repositories = uiState.data.items.map(repoMapper::mapToDomain)
                        if (repositories.isEmpty()) UiState.Empty
                        else UiState.Success(repositories)
                    }
                    is UiState.Failure -> uiState
                    is UiState.Loading -> uiState
                    is UiState.Empty -> uiState
                    is UiState.Idle -> uiState
                }
            }
            .catch { throwable ->
                    Log.e("GetRepositoriesUseCase", "Erro ao buscar repositórios", throwable)
                emit(
                    UiState.Failure(
                        errorCode = R.string.an_unexpected_error_occurred,
                        errorMessage = null
                    )
                )
            }
    }
}