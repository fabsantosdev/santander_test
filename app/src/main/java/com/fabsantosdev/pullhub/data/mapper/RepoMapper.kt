package com.fabsantosdev.pullhub.data.mapper

import android.util.Log
import com.fabsantosdev.pullhub.data.remote.dto.RepoDto
import com.fabsantosdev.pullhub.domain.model.Repo
import javax.inject.Inject

class RepoMapper @Inject constructor() {

    fun mapToDomain(repositoryDto: RepoDto): Repo {
        return try {
            Repo(
                id = repositoryDto.id,
                repositoryName = repositoryDto.name,
                repositoryDescription = repositoryDto.description ?: "",
                starsCount = repositoryDto.stargazersCount,
                imageUrl = repositoryDto.owner.avatarUrl,
                username = repositoryDto.owner.login,
                forkCount = repositoryDto.forksCount
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error ao usar o Mapper", e)
            throw e
        }
    }

    companion object {
        private const val TAG = "RepoMapper"
    }
}