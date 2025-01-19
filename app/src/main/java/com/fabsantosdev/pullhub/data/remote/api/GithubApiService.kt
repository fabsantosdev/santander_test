package com.fabsantosdev.pullhub.data.remote.api

import com.fabsantosdev.pullhub.data.remote.dto.GithubSearchResponse
import com.fabsantosdev.pullhub.data.remote.dto.PullRequestDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    //Busca repositórios do GitHub com filtros
    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") language: String = "Java",
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int = 1,
        @Query("per_page") itemsPerPage: Int = DEFAULT_PAGE_SIZE
    ): GithubSearchResponse


    //Busca pull requests de um repositório específico
    @GET("repos/{owner}/{repo}/pulls")
    suspend fun getPullRequests(
        @Path("owner") ownerName: String,
        @Path("repo") repositoryTitle: String,
        @Query("page") page: Int = 1,
        @Query("per_page") itemsPerPage: Int = DEFAULT_PAGE_SIZE,
        @Query("state") state: String = "all"
    ): List<PullRequestDto>

    companion object {
        const val DEFAULT_PAGE_SIZE = 30
    }
}