package com.fabsantosdev.pullhub.presentation.screens.repodetails

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fabsantosdev.pullhub.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryListTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackNavigate: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBackNavigate) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },

        modifier = modifier.fillMaxWidth()
    )
}