package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.common.presentation.MVIViewModel
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.GetHelloMessageUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.GetRandomGreetingUseCase

class HelloViewModel(
    private val getHelloMessageUseCase: GetHelloMessageUseCase,
    private val getRandomGreetingUseCase: GetRandomGreetingUseCase
) : MVIViewModel<HelloEvent, HelloState, HelloEffect>() {
    
    override fun setInitialState(): HelloState = HelloState()
    
    override fun handleEvents(event: HelloEvent) {
        when (event) {
            is HelloEvent.UpdateName -> updateName(event.name)
            is HelloEvent.GenerateMessage -> generateMessage()
            is HelloEvent.GenerateRandomGreeting -> generateRandomGreeting()
            is HelloEvent.ClearMessage -> clearMessage()
        }
    }
    
    private fun updateName(name: String) {
        setState { copy(name = name, error = null) }
    }
    
    private fun generateMessage() {
        setState { copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = getHelloMessageUseCase(currentState.name)) {
                is DataResult.Success -> {
                    setState { 
                        copy(
                            message = result.data, 
                            isLoading = false,
                            error = null
                        ) 
                    }
                    setEffect { HelloEffect.ShowToast("Message generated successfully!") }
                }
                is DataResult.Error -> {
                    setState { 
                        copy(
                            isLoading = false, 
                            error = result.message
                        ) 
                    }
                    setEffect { HelloEffect.ShowError(result.message) }
                }
            }
        }
    }
    
    private fun generateRandomGreeting() {
        setState { copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = getRandomGreetingUseCase()) {
                is DataResult.Success -> {
                    setState { 
                        copy(
                            message = result.data, 
                            isLoading = false,
                            error = null
                        ) 
                    }
                    setEffect { HelloEffect.ShowToast("Random greeting generated!") }
                }
                is DataResult.Error -> {
                    setState { 
                        copy(
                            isLoading = false, 
                            error = result.message
                        ) 
                    }
                    setEffect { HelloEffect.ShowError(result.message) }
                }
            }
        }
    }
    
    private fun clearMessage() {
        setState { copy(message = null, error = null) }
    }
}