package com.fabsantosdev.pullhub.domain.model

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data object Empty : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Failure(val errorCode: Int?, val errorMessage: String?) : UiState<Nothing>()
    data object Idle : UiState<Nothing>()
}