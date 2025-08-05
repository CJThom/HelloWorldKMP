package com.gpcasiapac.gpchelloworldkmp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginDestination
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginScreenContract
import com.gpcasiapac.gpchelloworldkmp.presentation.orders.OrdersScreen
import com.gpcasiapac.gpchelloworldkmp.presentation.cart.CartScreen

@Composable
fun AndroidAppNavigation() {
    val backStack = rememberNavBackStack<AndroidAppScreen>(AndroidAppScreen.Login)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<AndroidAppScreen.Login> {
                LoginDestination(
                    onNavigationRequested = { navigationEffect ->
                        when (navigationEffect) {
                            is LoginScreenContract.Effect.Navigation.NavigateToHome -> {
                                backStack.add(AndroidAppScreen.MainApp)
                            }
                        }
                    }
                )
            }
            
            entry<AndroidAppScreen.MainApp> {
                AndroidMainAppWithBottomNav()
            }
        }
    )
}

@Composable
private fun AndroidMainAppWithBottomNav() {
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

//@Composable
//fun AndroidAppNavigation() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = AndroidAppScreen.Login.toRoute()
//    ) {
//        composable(AndroidAppScreen.Login.toRoute()) {
//            LoginScreen(
//                onLoginClick = {
//                    navController.navigate(AndroidAppScreen.HelloWorld.toRoute()) {
//                        // Clear the login screen from the back stack
//                        popUpTo(AndroidAppScreen.Login.toRoute()) { inclusive = true }
//                    }
//                }
//            )
//        }
//
//        composable(AndroidAppScreen.HelloWorld.toRoute()) {
//            HelloDestination(
//                onNavigationRequested = { navigationEffect ->
//                    // Handle any navigation effects from HelloScreen
//                    // For now, we'll just ignore them since HelloScreen doesn't navigate anywhere
//                }
//            )
//        }
//    }
//}