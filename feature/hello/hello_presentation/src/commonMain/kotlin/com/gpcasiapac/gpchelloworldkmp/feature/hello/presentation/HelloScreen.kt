package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.mapper.toDetailedParams
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.mapper.toSimpleParams
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.DetailedMessageParams
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.HelloMessageState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelloScreen(
    state: HelloScreenContract.State,
    onEventSent: (HelloScreenContract.Event) -> Unit,
    effectFlow: Flow<HelloScreenContract.Effect>,
    onNavigationRequested: (HelloScreenContract.Effect.Navigation) -> Unit
) {
    // Handle side effects
    LaunchedEffect(effectFlow) {
        effectFlow.collect { effect ->
            when (effect) {
                is HelloScreenContract.Effect.ShowToast -> {
                    println("Toast: ${effect.message}")
                }
                is HelloScreenContract.Effect.ShowError -> {
                    println("Error: ${effect.error}")
                }
                is HelloScreenContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }
    }
    
    HelloScreenContent(
        viewState = state,
        onEvent = onEventSent
    )
}

@Composable
private fun HelloScreenContent(
    viewState: HelloScreenContract.State,
    onEvent: (HelloScreenContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello World KMP",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        InputSection(
            name = viewState.name,
            isLoading = viewState.isLoading,
            onNameChange = { onEvent(HelloScreenContract.Event.UpdateName(it)) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        ActionButtons(
            isLoading = viewState.isLoading,
            onGenerateMessage = { onEvent(HelloScreenContract.Event.GenerateMessage) },
            onGenerateRandom = { onEvent(HelloScreenContract.Event.GenerateRandomGreeting) }
        )
        
        if (viewState.message != null || viewState.error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { onEvent(HelloScreenContract.Event.ClearMessage) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Clear")
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        ResultSection(
            message = viewState.message,
            error = viewState.error
        )
    }
}

@Composable
private fun InputSection(
    name: String,
    isLoading: Boolean,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Enter your name") },
        modifier = Modifier.fillMaxWidth(),
        enabled = !isLoading
    )
}

@Composable
private fun ActionButtons(
    isLoading: Boolean,
    onGenerateMessage: () -> Unit,
    onGenerateRandom: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onGenerateMessage,
            enabled = !isLoading,
            modifier = Modifier.weight(1f)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Say Hello!")
            }
        }
        
        OutlinedButton(
            onClick = onGenerateRandom,
            enabled = !isLoading,
            modifier = Modifier.weight(1f)
        ) {
            Text("Random")
        }
    }
}

@Composable
private fun ResultSection(
    message: HelloMessageState?,
    error: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        message?.let { messageState ->
            // Simple component with direct parameters (≤3 params) - direct from State
            SimpleMessageCard(
                text = messageState.text,
                language = messageState.language,
                formattedTimestamp = messageState.formattedTimestamp
            )
            
            // Detailed component with params class (4+ params) - State → Params flow
            val detailedParams = messageState.toDetailedParams(System.currentTimeMillis())
            DetailedMessageCard(messageParams = detailedParams)
        }
        
        error?.let { errorMessage ->
            ErrorCard(error = errorMessage)
        }
    }
}

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
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Simple Card (≤3 params) - direct params",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Language: $language • Time: $formattedTimestamp",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun DetailedMessageCard(
    messageParams: DetailedMessageParams,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Detailed Card (4+ params)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = messageParams.text,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
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

@Composable
fun ErrorCard(
    error: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Text(
            text = "Error: $error",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.padding(16.dp)
        )
    }
}