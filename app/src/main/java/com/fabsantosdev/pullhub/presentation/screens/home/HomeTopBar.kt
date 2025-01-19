package com.fabsantosdev.pullhub.presentation.screens.home


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    title: String,
) {
    TopAppBar(
        title = { Text(text = title) }, modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun RepositoryListToolbarPreview() {
    HomeTopBar(modifier = Modifier.fillMaxSize(), "Repositórios")
}