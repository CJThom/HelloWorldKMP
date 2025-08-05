package com.gpcasiapac.gpchelloworldkmp.app.pos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.gpcasiapac.gpchelloworldkmp.app.pos.presentation.cart.CartScreen
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginDestination
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginScreenContract

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
