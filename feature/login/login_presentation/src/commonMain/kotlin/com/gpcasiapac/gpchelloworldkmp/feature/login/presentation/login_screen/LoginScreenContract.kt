package com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen

import androidx.compose.runtime.Immutable
import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewState
import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewEvent
import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewSideEffect
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.model.LoginState

object LoginScreenContract {

    @Immutable
    data class State(
        val loginState: LoginState,
        val error: String?
    ) : ViewState
    
    sealed interface Event : ViewEvent {
        data class UpdateUsername(val username: String) : Event
        data class UpdatePassword(val password: String) : Event
        data object Login : Event
        data object ClearError : Event
    }
    
    sealed interface Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect
        data class ShowError(val error: String) : Effect
        
        sealed interface Navigation : Effect {
            data object NavigateToHome : Navigation
        }
    }
}