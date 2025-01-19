package com.fabsantosdev.pullhub.domain.model

data class Repo(
    val id: Long,
    val repositoryName: String,
    val repositoryDescription: String,
    val starsCount: Int,
    val forkCount: Int,
    val imageUrl: String,
    val username: String
)