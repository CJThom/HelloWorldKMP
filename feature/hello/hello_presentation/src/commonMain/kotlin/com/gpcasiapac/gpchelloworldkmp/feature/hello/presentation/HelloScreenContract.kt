package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation

import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewState
import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewEvent
import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewSideEffect
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.model.HelloMessageState

object HelloScreenContract {
    data class State(
        val name: String,
        val message: HelloMessageState?,
        val isLoading: Boolean,
        val error: String?
    ) : ViewState
    
    sealed interface Event : ViewEvent {
        data class UpdateName(val name: String) : Event
        data object GenerateMessage : Event
        data object GenerateRandomGreeting : Event
        data object ClearMessage : Event
    }
    
    sealed interface Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect
        data class ShowError(val error: String) : Effect
        
        sealed interface Navigation : Effect {
            // Add navigation effects here as needed
            // Example: data object NavigateBack : Navigation
            // Example: data class NavigateToDetails(val id: String) : Navigation
        }
    }
}