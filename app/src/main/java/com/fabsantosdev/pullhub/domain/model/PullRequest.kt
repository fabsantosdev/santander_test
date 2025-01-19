package com.fabsantosdev.pullhub.domain.model

data class PullRequest(
    val id: Long,
    val title: String,
    val description: String,
    val username: String,
    val userAvatarUrl: String,
    val createdAt: String,
    val state: PullRequestState = PullRequestState.ALL
)
