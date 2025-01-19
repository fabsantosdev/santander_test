package com.fabsantosdev.pullhub.domain.usecase

import com.fabsantosdev.pullhub.domain.model.Repo
import com.fabsantosdev.pullhub.domain.model.UiState
import kotlinx.coroutines.flow.Flow

interface BaseUseCase<in Params, out Type> {
    suspend fun execute(params: Params): Flow<UiState<Type>>
}

interface GetRepositoriesUseCase : BaseUseCase<GetRepositoriesUseCase.Params, List<Repo>> {
    data class Params(
        val pageNumber: Int,
        val language: String = "Java",
        val itemsPerPage: Int = DEFAULT_PAGE_SIZE
    ) {
        companion object {
            const val DEFAULT_PAGE_SIZE = 30
        }
    }
}

