package com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.gpcasiapac.gpchelloworldkmp.common.presentation.MVIViewModel
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.LoginUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.model.LoginState
import com.gpcasiapac.gpchelloworldkmp.feature.login.api.LoginService

class LoginViewModel(
    private val loginService: LoginService
) : MVIViewModel<LoginScreenContract.Event, LoginScreenContract.State, LoginScreenContract.Effect>() {
    
    override fun setInitialState(): LoginScreenContract.State = LoginScreenContract.State(
        loginState = LoginState.initial(),
        error = null
    )
    
    override suspend fun awaitReadiness(): Boolean {
        // Login screen doesn't require session readiness
        return true
    }
    
    override fun handleReadinessFailed() {
        // Not applicable for login screen
    }
    
    override fun onStart() {
        // Initialize any startup logic here if needed
    }
    
    // TABLE OF CONTENTS - All possible events handled here
    override fun handleEvents(event: LoginScreenContract.Event) {
        when (event) {
            is LoginScreenContract.Event.UpdateUsername -> updateUsername(event.username)
            is LoginScreenContract.Event.UpdatePassword -> updatePassword(event.password)
            is LoginScreenContract.Event.Login -> {
                viewModelScope.launch {
                    performLogin(
                        onSuccess = {
                            setEffect { LoginScreenContract.Effect.ShowToast("Login successful!") }
                            setEffect { LoginScreenContract.Effect.Navigation.NavigateToHome }
                        },
                        onError = { errorMessage ->
                            setEffect { LoginScreenContract.Effect.ShowError(errorMessage) }
                        }
                    )
                }
            }
            is LoginScreenContract.Event.ClearError -> clearError()
        }
    }
    
    private fun updateUsername(username: String) {
        setState { 
            copy(
                loginState = loginState.copy(
                    username = username,
                    isLoginEnabled = username.isNotBlank() && loginState.password.isNotBlank()
                ),
                error = null
            )
        }
    }
    
    private fun updatePassword(password: String) {
        setState { 
            copy(
                loginState = loginState.copy(
                    password = password,
                    isLoginEnabled = loginState.username.isNotBlank() && password.isNotBlank()
                ),
                error = null
            )
        }
    }
    
    private suspend fun performLogin(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        setState { 
            copy(
                loginState = loginState.copy(isLoading = true),
                error = null
            )
        }
        
        when (val result = loginService.login(viewState.value.loginState.username, viewState.value.loginState.password)) {
            is LoginUseCase.UseCaseResult.Success -> {
                setState { 
                    copy(
                        loginState = loginState.copy(isLoading = false),
                        error = null
                    ) 
                }
                onSuccess()
            }
            is LoginUseCase.UseCaseResult.Error -> {
                setState { 
                    copy(
                        loginState = loginState.copy(isLoading = false),
                        error = result.message
                    ) 
                }
                onError(result.message)
            }
        }
    }
    
    private fun clearError() {
        setState { copy(error = null) }
    }
}
