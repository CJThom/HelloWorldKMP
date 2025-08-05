package com.gpcasiapac.gpchelloworldkmp.app.pos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.gpcasiapac.gpchelloworldkmp.app.pos.presentation.login.LoginScreen
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen.HelloDestination

// Extension functions to convert type-safe keys to string routes
private fun AndroidAppScreen.toRoute(): String = when (this) {
    AndroidAppScreen.Login -> "android_login"
    AndroidAppScreen.HelloWorld -> "android_hello_world"
}


@Composable
fun AndroidAppNavigation() {
    // 6-a  create & remember the back-stack, starting on Login
    val backStack = rememberNavBackStack<AndroidAppScreen>(AndroidAppScreen.Login)

    // 6-b  render the current entry
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },      // system back
        entryProvider = entryProvider {
            entry<AndroidAppScreen.Login> {
                LoginScreen { backStack.add(AndroidAppScreen.HelloWorld) }
            }
            entry<AndroidAppScreen.HelloWorld> {
                HelloDestination(
                    onNavigationRequested = { navigationEffect ->
                        // Handle any navigation effects from HelloScreen
                        // For now, we'll just ignore them since HelloScreen doesn't navigate anywhere
                    }
                )
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