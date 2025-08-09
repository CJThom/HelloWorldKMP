package com.gpcasiapac.gpchelloworldkmp.app.pickup.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.gpcasiapac.gpchelloworldkmp.app.pickup.presentation.orders.OrdersScreen
import com.gpcasiapac.gpchelloworldkmp.feature.login.api.LoginFeatureEntry
import org.koin.compose.koinInject

sealed class Screen {
    object Login : Screen()
    object Orders : Screen()
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    when (currentScreen) {
        Screen.Login -> {
            val loginEntry: LoginFeatureEntry = koinInject()
            loginEntry.Graph(onLoggedIn = { currentScreen = Screen.Orders })
        }
        
        Screen.Orders -> {
            OrdersScreen()
        }
    }
}
