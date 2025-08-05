package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gpcasiapac.gpchelloworldkmp.common.presentation.Dimens
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.HelloMessageParams

@Composable
fun DetailedMessageCard(
    messageParams: HelloMessageParams,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimens.Space.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Detailed Card (4+ params)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(Dimens.Space.extraSmall))
            
            Text(
                text = messageParams.text,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(Dimens.Space.small))
            
            Text(
                text = "Language: ${messageParams.language} • Time: ${messageParams.formattedTimestamp}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "ID: ${messageParams.messageId} • User: ${if (messageParams.isFromCurrentUser) "You" else "Other"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
            )
        }
    }
}