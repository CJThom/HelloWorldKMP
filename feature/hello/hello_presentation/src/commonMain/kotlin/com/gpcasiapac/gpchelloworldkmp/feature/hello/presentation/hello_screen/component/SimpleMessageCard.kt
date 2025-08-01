package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gpcasiapac.gpchelloworldkmp.common.presentation.Dimens

@Composable
fun SimpleMessageCard(
    text: String,
    language: String,
    formattedTimestamp: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimens.Space.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Simple Card (≤3 params) - direct params",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(Dimens.Space.extraSmall))
            
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            
            Spacer(modifier = Modifier.height(Dimens.Space.small))
            
            Text(
                text = "Language: $language • Time: $formattedTimestamp",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}