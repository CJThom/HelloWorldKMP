package com.gpcasiapac.gpchelloworldkmp.feature.login.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gpcasiapac.gpchelloworldkmp.feature.login.api.LoginFeatureEntry
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginDestination
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginScreenContract

private object LoginRoutes {
    const val Login = "login"
}

class LoginFeatureEntryImpl : LoginFeatureEntry {

    @Composable
    override fun Screen(onLoggedIn: () -> Unit) {
        LoginDestination(
            onNavigationRequested = { navigationEffect ->
                when (navigationEffect) {
                    is LoginScreenContract.Effect.Navigation.NavigateToHome -> onLoggedIn()
                }
            }
        )
    }

    @Composable
    override fun Graph(
        modifier: Modifier,
        onLoggedIn: () -> Unit
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = LoginRoutes.Login) {
            composable(LoginRoutes.Login) {
                Screen(onLoggedIn)
            }
        }
    }
}
