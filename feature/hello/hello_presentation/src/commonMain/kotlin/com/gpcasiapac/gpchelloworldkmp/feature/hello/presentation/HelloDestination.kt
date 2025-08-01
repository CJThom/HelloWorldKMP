package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HelloDestination(
    helloScreenViewModel: HelloViewModel = koinViewModel(),
    onNavigationRequested: (navigationEffect: HelloScreenContract.Effect.Navigation) -> Unit = {}
) {
    HelloScreen(
        state = helloScreenViewModel.viewState.collectAsState().value,
        onEventSent = { event -> helloScreenViewModel.setEvent(event) },
        effectFlow = helloScreenViewModel.effect,
        onNavigationRequested = onNavigationRequested
    )
}