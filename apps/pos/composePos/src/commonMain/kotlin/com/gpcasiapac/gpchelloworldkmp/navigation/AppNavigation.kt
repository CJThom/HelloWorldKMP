package com.gpcasiapac.gpchelloworldkmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginDestination
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginScreenContract
import com.gpcasiapac.gpchelloworldkmp.presentation.cart.CartScreen

sealed class Screen {
    object Login : Screen()
    object Cart : Screen()
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    when (currentScreen) {
        Screen.Login -> {
            LoginDestination(
                onNavigationRequested = { navigationEffect ->
                    when (navigationEffect) {
                        is LoginScreenContract.Effect.Navigation.NavigateToHome -> {
                            currentScreen = Screen.Cart
                        }
                    }
                }
            )
        }
        
        Screen.Cart -> {
            CartScreen()
        }
    }
}
