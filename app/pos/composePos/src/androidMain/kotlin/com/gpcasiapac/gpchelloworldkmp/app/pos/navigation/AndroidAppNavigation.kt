package com.gpcasiapac.gpchelloworldkmp.app.pos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.gpcasiapac.gpchelloworldkmp.feature.login.api.LoginFeatureEntry
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.CartFeatureEntry
import org.koin.compose.koinInject

@Composable
fun AndroidAppNavigation() {
    // Create & remember the back-stack, starting on Login
    val backStack = rememberNavBackStack<AndroidAppScreen>(AndroidAppScreen.Login)

    // Render the current entry using Navigation 3 and feature API entries
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<AndroidAppScreen.Login> {
                val loginEntry: LoginFeatureEntry = koinInject()
                loginEntry.Graph(onLoggedIn = { backStack.add(AndroidAppScreen.HelloWorld) })
            }
            entry<AndroidAppScreen.HelloWorld> {
                val cartEntry: CartFeatureEntry = koinInject()
                cartEntry.Graph()
            }
        }
    )
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