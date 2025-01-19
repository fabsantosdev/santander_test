package com.fabsantosdev.pullhub.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GithubSearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<RepoDto>
)