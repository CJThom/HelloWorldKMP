package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.mapper.toParams

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelloScreen() {
    val viewModel = koinViewModel<HelloViewModel>()
    val state by viewModel.viewState.collectAsState()
    
    // Handle side effects
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HelloEffect.ShowToast -> {
                    println("Toast: ${effect.message}")
                }
                is HelloEffect.ShowError -> {
                    println("Error: ${effect.error}")
                }
            }
        }
    }
    
    val screenState = HelloScreenContract.State(
        name = state.name,
        message = state.message,
        isLoading = state.isLoading,
        error = state.error
    )
    
    HelloScreenContent(
        viewState = screenState,
        onEvent = viewModel::setEvent
    )
}

@Composable
private fun HelloScreenContent(
    viewState: HelloScreenContract.State,
    onEvent: (HelloEvent) -> Unit
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
            onNameChange = { onEvent(HelloEvent.UpdateName(it)) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        ActionButtons(
            isLoading = viewState.isLoading,
            onGenerateMessage = { onEvent(HelloEvent.GenerateMessage) },
            onGenerateRandom = { onEvent(HelloEvent.GenerateRandomGreeting) }
        )
        
        if (viewState.message != null || viewState.error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { onEvent(HelloEvent.ClearMessage) },
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
    message: com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage?,
    error: String?
) {
    message?.let { helloMessage ->
        val messageParams = helloMessage.toParams()
        HelloMessageCard(messageParams = messageParams)
    }
    
    error?.let { errorMessage ->
        ErrorCard(error = errorMessage)
    }
}

@Composable
fun HelloMessageCard(
    messageParams: com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.HelloMessageParams
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
        }
    }
}

@Composable
fun ErrorCard(error: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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