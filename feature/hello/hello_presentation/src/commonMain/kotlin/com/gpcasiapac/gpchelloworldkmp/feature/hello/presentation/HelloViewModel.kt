package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.gpcasiapac.gpchelloworldkmp.common.presentation.MVIViewModel
import com.gpcasiapac.gpchelloworldkmp.common.presentation.SessionHandler
import com.gpcasiapac.gpchelloworldkmp.common.presentation.SessionHandlerDelegate
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloMessage
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.model.HelloSessionIds
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.GetHelloMessageUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.GetRandomGreetingUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.GetSessionIdsFlowUseCase

class HelloViewModel(
    private val getHelloMessageUseCase: GetHelloMessageUseCase,
    private val getRandomGreetingUseCase: GetRandomGreetingUseCase,
    private val getSessionIdsFlowUseCase: GetSessionIdsFlowUseCase
) : MVIViewModel<HelloEvent, HelloState, HelloEffect>(),
    SessionHandlerDelegate<HelloSessionIds> by SessionHandler(
        initialSession = HelloSessionIds(),
        sessionFlow = getSessionIdsFlowUseCase()
    ) {
    
    override fun setInitialState(): HelloState = HelloState()
    
    override suspend fun awaitReadiness(): Boolean {
        val sessionIds = sessionState.first { it.userId != null && it.isAuthenticated }
        return sessionIds.userId != null && sessionIds.isAuthenticated
    }
    
    override fun handleReadinessFailed() {
        setState { copy(error = "Session not ready - please wait for authentication") }
    }
    
    override fun onStart() {
        // Initialize any startup logic here
        // Session is guaranteed to be ready when this is called
    }
    
    // TABLE OF CONTENTS - All possible events handled here
    override fun handleEvents(event: HelloEvent) {
        when (event) {
            is HelloEvent.UpdateName -> updateName(event.name)
            is HelloEvent.GenerateMessage -> {
                viewModelScope.launch {
                    generateMessage(
                        onSuccess = { message ->
                            setState { 
                                copy(
                                    message = message, 
                                    isLoading = false,
                                    error = null
                                ) 
                            }
                            setEffect { HelloEffect.ShowToast("Message generated successfully!") }
                        },
                        onError = { errorMessage ->
                            setState { 
                                copy(
                                    isLoading = false, 
                                    error = errorMessage
                                ) 
                            }
                            setEffect { HelloEffect.ShowError(errorMessage) }
                        }
                    )
                }
            }
            is HelloEvent.GenerateRandomGreeting -> {
                viewModelScope.launch {
                    generateRandomGreeting(
                        onSuccess = { message ->
                            setState { 
                                copy(
                                    message = message, 
                                    isLoading = false,
                                    error = null
                                ) 
                            }
                            setEffect { HelloEffect.ShowToast("Random greeting generated!") }
                        },
                        onError = { errorMessage ->
                            setState { 
                                copy(
                                    isLoading = false, 
                                    error = errorMessage
                                ) 
                            }
                            setEffect { HelloEffect.ShowError(errorMessage) }
                        }
                    )
                }
            }
            is HelloEvent.ClearMessage -> clearMessage()
        }
    }
    
    private fun updateName(name: String) {
        setState { copy(name = name, error = null) }
    }
    
    private suspend fun generateMessage(
        onSuccess: (HelloMessage) -> Unit,
        onError: (String) -> Unit
    ) {
        setState { copy(isLoading = true, error = null) }
        
        when (val result = getHelloMessageUseCase(viewState.value.name)) {
            is GetHelloMessageUseCase.UseCaseResult.Success -> {
                onSuccess(result.helloMessage)
            }
            is GetHelloMessageUseCase.UseCaseResult.Error -> {
                onError(result.message)
            }
        }
    }
    
    private suspend fun generateRandomGreeting(
        onSuccess: (HelloMessage) -> Unit,
        onError: (String) -> Unit
    ) {
        setState { copy(isLoading = true, error = null) }
        
        when (val result = getRandomGreetingUseCase()) {
            is GetRandomGreetingUseCase.UseCaseResult.Success -> {
                onSuccess(result.helloMessage)
            }
            is GetRandomGreetingUseCase.UseCaseResult.Error -> {
                onError(result.message)
            }
        }
    }
    
    private fun clearMessage() {
        setState { copy(message = null, error = null) }
    }
}