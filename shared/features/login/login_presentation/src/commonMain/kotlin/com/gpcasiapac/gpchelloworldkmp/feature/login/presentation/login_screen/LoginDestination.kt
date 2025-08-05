package com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginDestination(
    loginViewModel: LoginViewModel = koinViewModel(),
    onNavigationRequested: (navigationEffect: LoginScreenContract.Effect.Navigation) -> Unit = {}
) {
    LoginScreen(
        state = loginViewModel.viewState.collectAsState().value,
        onEventSent = { event -> loginViewModel.setEvent(event) },
        effectFlow = loginViewModel.effect,
        onNavigationRequested = onNavigationRequested
    )
}