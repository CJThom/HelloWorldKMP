package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen

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
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.ObservePeriodicGreetingsUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.mapper.toState

class HelloViewModel(
    private val getHelloMessageUseCase: GetHelloMessageUseCase,
    private val getRandomGreetingUseCase: GetRandomGreetingUseCase,
    private val getSessionIdsFlowUseCase: GetSessionIdsFlowUseCase,
    private val observePeriodicGreetingsUseCase: ObservePeriodicGreetingsUseCase
) : MVIViewModel<HelloScreenContract.Event, HelloScreenContract.State, HelloScreenContract.Effect>(),
    SessionHandlerDelegate<HelloSessionIds> by SessionHandler(
        initialSession = HelloSessionIds(),
        sessionFlow = getSessionIdsFlowUseCase()
    ) {
    
    override fun setInitialState(): HelloScreenContract.State = HelloScreenContract.State(
        name = "",
        helloMessageState = null,
        isLoading = false,
        error = null
    )
    
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
        
        viewModelScope.launch {
            observePeriodicGreetings(
                onGreetingReceived = {
                    setEffect { HelloScreenContract.Effect.ShowToast("New greeting received!") }
                }
            )
        }
    }
    
    // TABLE OF CONTENTS - All possible events handled here
    override fun handleEvents(event: HelloScreenContract.Event) {
        when (event) {
            is HelloScreenContract.Event.UpdateName -> updateName(event.name)
            is HelloScreenContract.Event.GenerateMessage -> {
                viewModelScope.launch {
                    generateMessage(
                        onSuccess = {
                            setEffect { HelloScreenContract.Effect.ShowToast("Message generated successfully!") }
                        },
                        onError = { errorMessage ->
                            setEffect { HelloScreenContract.Effect.ShowError(errorMessage) }
                        }
                    )
                }
            }
            is HelloScreenContract.Event.GenerateRandomGreeting -> {
                viewModelScope.launch {
                    generateRandomGreeting(
                        onSuccess = {
                            setEffect { HelloScreenContract.Effect.ShowToast("Random greeting generated!") }
                        },
                        onError = { errorMessage ->
                            setEffect { HelloScreenContract.Effect.ShowError(errorMessage) }
                        }
                    )
                }
            }
            is HelloScreenContract.Event.ClearMessage -> clearMessage()
        }
    }
    
    private suspend fun observePeriodicGreetings(
        onGreetingReceived: () -> Unit
    ) {
        observePeriodicGreetingsUseCase().collect { helloMessage ->
            setState {
                copy(
                    helloMessageState = helloMessage.toState(),
                    error = null
                )
            }
            onGreetingReceived()
        }
    }
    
    private fun updateName(name: String) {
        setState { copy(name = name, error = null) }
    }
    
    private suspend fun generateMessage(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        setState { copy(isLoading = true, error = null) }
        
        when (val result = getHelloMessageUseCase(viewState.value.name)) {
            is GetHelloMessageUseCase.UseCaseResult.Success -> {
                setState { 
                    copy(
                        helloMessageState = result.helloMessage.toState(),
                        isLoading = false,
                        error = null
                    ) 
                }
                onSuccess()
            }
            is GetHelloMessageUseCase.UseCaseResult.Error -> {
                setState { 
                    copy(
                        isLoading = false, 
                        error = result.message
                    ) 
                }
                onError(result.message)
            }
        }
    }
    
    private suspend fun generateRandomGreeting(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        setState { copy(isLoading = true, error = null) }
        
        when (val result = getRandomGreetingUseCase()) {
            is GetRandomGreetingUseCase.UseCaseResult.Success -> {
                setState { 
                    copy(
                        helloMessageState = result.helloMessage.toState(),
                        isLoading = false,
                        error = null
                    ) 
                }
                onSuccess()
            }
            is GetRandomGreetingUseCase.UseCaseResult.Error -> {
                setState { 
                    copy(
                        isLoading = false, 
                        error = result.message
                    ) 
                }
                onError(result.message)
            }
        }
    }
    
    private fun clearMessage() {
        setState { copy(helloMessageState = null, error = null) }
    }
}