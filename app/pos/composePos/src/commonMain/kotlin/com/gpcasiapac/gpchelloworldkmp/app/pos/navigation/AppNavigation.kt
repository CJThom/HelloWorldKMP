package com.gpcasiapac.gpchelloworldkmp.app.pos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.CartFeatureEntry
import com.gpcasiapac.gpchelloworldkmp.feature.login.api.LoginFeatureEntry
import org.koin.compose.koinInject

private object PosRoutes {
    const val Login = "pos_login"
    const val Cart = "pos_cart"
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = PosRoutes.Login) {
        composable(PosRoutes.Login) {
            val loginEntry: LoginFeatureEntry = koinInject()
            loginEntry.Graph(onLoggedIn = {
                navController.navigate(PosRoutes.Cart) {
                    popUpTo(PosRoutes.Login) { inclusive = true }
                }
            })
        }
        composable(PosRoutes.Cart) {
            val cartEntry: CartFeatureEntry = koinInject()
            cartEntry.Graph(modifier = modifier)
        }
    }
}
