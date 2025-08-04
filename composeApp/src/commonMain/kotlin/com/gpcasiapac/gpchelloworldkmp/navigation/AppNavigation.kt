package com.gpcasiapac.gpchelloworldkmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.gpcasiapac.gpchelloworldkmp.presentation.login.LoginScreen
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen.HelloDestination

sealed class Screen {
    object Login : Screen()
    object Hello : Screen()
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    when (currentScreen) {
        Screen.Login -> {
            LoginScreen(
                onLoginClick = {
                    currentScreen = Screen.Hello
                }
            )
        }
        
        Screen.Hello -> {
            HelloDestination(
                onNavigationRequested = { navigationEffect ->
                    // Handle any navigation effects from HelloScreen
                    // For now, we'll just ignore them since HelloScreen doesn't navigate anywhere
                }
            )
        }
    }
}
