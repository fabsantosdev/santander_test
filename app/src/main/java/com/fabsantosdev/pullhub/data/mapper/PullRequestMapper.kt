package com.fabsantosdev.pullhub.data.mapper

import android.util.Log
import androidx.core.content.ContextCompat.getString
import com.fabsantosdev.pullhub.R
import com.fabsantosdev.pullhub.data.remote.dto.PullRequestDto
import com.fabsantosdev.pullhub.domain.model.PullRequest
import com.fabsantosdev.pullhub.domain.model.PullRequestState
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.util.Locale
import javax.inject.Inject


class PullRequestMapper @Inject constructor() {

    fun mapToDomain(dto: PullRequestDto): PullRequest {
        return try {
            PullRequest(
                id = dto.id,
                title = dto.title,
                description = dto.body ?: "",
                username = dto.user.login,
                userAvatarUrl = dto.user.avatarUrl,
                createdAt = formatDynamicDate(dto.createdAt),
                state = PullRequestState.fromString(dto.state)
            )
        } catch (e: Exception) {
            throw e
        }
    }

    private fun formatDynamicDate(timestamp: String): String {
        return try {
            val parsedDate = ZonedDateTime.parse(timestamp)
            val now = ZonedDateTime.now()
            val daysBetween = ChronoUnit.DAYS.between(parsedDate.toLocalDate(), now.toLocalDate())

            when (daysBetween) {
                0L -> "Hoje"
                1L -> "Ontem"
                in 2..7 -> "Há $daysBetween dias"
                else -> parsedDate.format(DATE_FORMATTER)
            }
        } catch (e: DateTimeParseException) {
            Log.e(TAG, "Erro ao analisar data: $timestamp", e)
            "Data indisponível"
        }
    }

    companion object {
        private const val TAG = "PullRequestMapper"
        private val DATE_FORMATTER = DateTimeFormatter.ofPattern("d 'de' MMM 'de' yyyy", Locale("pt", "BR"))
    }
}