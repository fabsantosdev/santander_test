package com.fabsantosdev.pullhub.presentation.common.components.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseScreenLayout(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(modifier = modifier, topBar = {
        topBar()
    }) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            content(padding)
        }
    }
}