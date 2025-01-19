package com.fabsantosdev.pullhub.domain.model

data class PullRequestQuery(
    val ownerName: String,
    val repositoryTitle: String,
    val pageNumber: Int = 1
)