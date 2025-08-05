package com.gpcasiapac.gpchelloworldkmp.app.pickup.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginDestination
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginScreenContract
import com.gpcasiapac.gpchelloworldkmp.app.pickup.presentation.orders.OrdersScreen


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
                LoginDestination(
                    onNavigationRequested = { navigationEffect ->
                        when (navigationEffect) {
                            is LoginScreenContract.Effect.Navigation.NavigateToHome -> {
                                backStack.add(AndroidAppScreen.Orders)
                            }
                        }
                    }
                )
            }
            entry<AndroidAppScreen.Orders> {
                OrdersScreen()
            }
        }
    )
}