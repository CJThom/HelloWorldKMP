package com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.model

data class LoginState(
    val username: String,
    val password: String,
    val isLoading: Boolean,
    val isLoginEnabled: Boolean
) {
    companion object {
        fun initial() = LoginState(
            username = "",
            password = "",
            isLoading = false,
            isLoginEnabled = false
        )
    }
}