package com.fabsantosdev.pullhub.presentation.state

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.fabsantosdev.pullhub.R
import com.fabsantosdev.pullhub.domain.model.UiState

@Composable
fun <T> HandleUiState(
    uiState: UiState<Any>,
    data: List<T>,
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable () -> Unit,
    onFailure: @Composable (Int) -> Unit,
    onEmpty: @Composable () -> Unit = {  }
) {
    when (uiState) {
        is UiState.Loading -> onLoading()
        is UiState.Success -> onSuccess()
        is UiState.Failure -> onFailure(uiState.errorCode ?: (R.string.empty))
        UiState.Empty -> onEmpty()
        UiState.Idle -> onEmpty()
    }
}