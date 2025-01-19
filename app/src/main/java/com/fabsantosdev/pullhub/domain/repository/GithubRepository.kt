package com.fabsantosdev.pullhub.domain.repository

import com.fabsantosdev.pullhub.data.remote.dto.GithubSearchResponse
import com.fabsantosdev.pullhub.data.remote.dto.PullRequestDto
import com.fabsantosdev.pullhub.domain.model.PullRequestQuery
import com.fabsantosdev.pullhub.domain.model.UiState
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    //Busca repositórios do GitHub de acordo com a linguagem especificada
    suspend fun getRepositories(
        pageNumber: Int,
        language: String = "Java",
        itemsPerPage: Int = DEFAULT_PAGE_SIZE
    ): Flow<UiState<GithubSearchResponse>>

    //Busca pull requests de um repositório específico
    suspend fun getPullRequests(
        pullRequestQuery: PullRequestQuery,
        itemsPerPage: Int = DEFAULT_PAGE_SIZE
    ): Flow<UiState<List<PullRequestDto>>>

    companion object {
        const val DEFAULT_PAGE_SIZE = 30
    }
}