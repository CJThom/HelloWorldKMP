package com.gpcasiapac.gpchelloworldkmp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginDestination
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginScreenContract
import com.gpcasiapac.gpchelloworldkmp.presentation.orders.OrdersScreen
import com.gpcasiapac.gpchelloworldkmp.presentation.cart.CartScreen

sealed class Screen {
    object Login : Screen()
    object MainApp : Screen()
}

sealed class MainAppTab {
    object Pos : MainAppTab()
    object Pickup : MainAppTab()
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
                            currentScreen = Screen.MainApp
                        }
                    }
                }
            )
        }
        
        Screen.MainApp -> {
            MainAppWithBottomNav()
        }
    }
}

@Composable
private fun MainAppWithBottomNav() {
    var selectedTab by remember { mutableStateOf<MainAppTab>(MainAppTab.Pos) } // POS is default
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                    label = { Text("POS") },
                    selected = selectedTab == MainAppTab.Pos,
                    onClick = { selectedTab = MainAppTab.Pos }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LocalShipping, contentDescription = null) },
                    label = { Text("Pickup") },
                    selected = selectedTab == MainAppTab.Pickup,
                    onClick = { selectedTab = MainAppTab.Pickup }
                )
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            MainAppTab.Pos -> {
                CartScreen(modifier = Modifier.padding(paddingValues))
            }
            MainAppTab.Pickup -> {
                OrdersScreen(modifier = Modifier.padding(paddingValues))
            }
        }
    }
}
