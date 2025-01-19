package com.fabsantosdev.pullhub.presentation.common.components.pullrequest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import coil.compose.rememberAsyncImagePainter
import com.fabsantosdev.pullhub.R
import java.util.Locale

@Composable
fun PullRequestCard(
    title: String,
    description: String,
    username: String,
    createdAt: String,
    pullRequestState: String,
    imageUrl: String,
    onClickListener: (id: Long) -> Unit
) {
    Card(
        onClick = { onClickListener(0L) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.semantics { heading() }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.medium),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.semantics {
                    contentDescription = "Pull request description: $description"
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = stringResource(R.string.avatar_of, username),
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.by, username, createdAt),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.medium)
                    )
                }

                val stateColor = when (pullRequestState.lowercase(Locale.ROOT)) {
                    stringResource(R.string.open) -> MaterialTheme.colorScheme.primary
                    stringResource(R.string.closed) -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.medium)
                }

                val formattedState = pullRequestState.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                }

                Text(
                    text = formattedState,
                    style = MaterialTheme.typography.labelMedium,
                    color = stateColor,
                    modifier = Modifier
                        .background(
                            color = stateColor.copy(alpha = 0.15f), // Increased for better contrast
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .semantics {
                            contentDescription = "Status do pull request $pullRequestState"
                        }
                )
            }
        }
    }
}
@Preview
@Composable
fun PullRequestCardPreview() {
    PullRequestCard(
        title = "Adicionar nova funcionalidade: Autenticação de Usuário",
        description = "Este pull request introduz um fluxo de autenticação de usuário com integrações do Google e Facebook.",
        username = "João",
        createdAt = "16 de janeiro de 2025",
        pullRequestState = "aberto",
        onClickListener = { id -> println("Pull Request $id clicado!") },
        imageUrl = ""
    )
}
