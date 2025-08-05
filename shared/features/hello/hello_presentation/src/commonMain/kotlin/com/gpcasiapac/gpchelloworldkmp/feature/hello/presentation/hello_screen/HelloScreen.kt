package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow
import com.gpcasiapac.gpchelloworldkmp.common.presentation.Dimens
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.mapper.toParams
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.HelloMessageState
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen.component.SimpleMessageCard
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen.component.DetailedMessageCard
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen.component.ErrorCard


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
            .padding(Dimens.Layout.screenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello World KMP",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = Dimens.Space.extraLarge)
        )
        
        InputSection(
            name = viewState.name,
            isLoading = viewState.isLoading,
            onNameChange = { onEvent(HelloScreenContract.Event.UpdateName(it)) }
        )
        
        Spacer(modifier = Modifier.height(Dimens.Space.medium))
        
        ActionButtons(
            isLoading = viewState.isLoading,
            onGenerateMessage = { onEvent(HelloScreenContract.Event.GenerateMessage) },
            onGenerateRandom = { onEvent(HelloScreenContract.Event.GenerateRandomGreeting) }
        )
        
        if (viewState.helloMessageState != null || viewState.error != null) {
            Spacer(modifier = Modifier.height(Dimens.Space.medium))
            
            Button(
                onClick = { onEvent(HelloScreenContract.Event.ClearMessage) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Clear")
            }
        }
        
        Spacer(modifier = Modifier.height(Dimens.Layout.sectionSpacing))
        
        ResultSection(
            helloMessageState = viewState.helloMessageState,
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
        horizontalArrangement = Arrangement.spacedBy(Dimens.Space.small)
    ) {
        Button(
            onClick = onGenerateMessage,
            enabled = !isLoading,
            modifier = Modifier.weight(1f)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimens.Size.iconSmall),
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
    helloMessageState: HelloMessageState?,
    error: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.Space.medium)
    ) {
        helloMessageState?.let { messageState ->
            // Simple component with direct parameters (≤3 params) - direct from State
            SimpleMessageCard(
                text = messageState.text,
                language = messageState.language,
                formattedTimestamp = messageState.formattedTimestamp
            )
            
            // Detailed component with params class (4+ params) - State → Params flow
            val detailedParams = messageState.toParams(System.currentTimeMillis())
            DetailedMessageCard(messageParams = detailedParams)
        }
        
        error?.let { errorMessage ->
            ErrorCard(error = errorMessage)
        }
    }
}
