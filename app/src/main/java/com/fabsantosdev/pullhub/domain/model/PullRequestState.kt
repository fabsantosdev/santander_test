package com.fabsantosdev.pullhub.domain.model

enum class PullRequestState(val displayName: String) {
    OPEN("Open"),
    CLOSED("Closed"),
    ALL("All");

    companion object {
        fun fromString(state: String): PullRequestState {
            return entries.firstOrNull { it.name.equals(state, ignoreCase = true) }
                ?: ALL
        }
    }
}
