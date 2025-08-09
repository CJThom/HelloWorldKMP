package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.OrderId
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.PosCartDestination
import kotlinx.serialization.Serializable

@Serializable
sealed interface CartRoutesJvm  {
    @Serializable data object Cart : CartRoutesJvm
    @Serializable data class Checkout(val orderId: String) : CartRoutesJvm
}

@Composable
actual fun CartGraphContent(
    modifier: Modifier,
    startDestination: PosCartDestination,
) {
    val navController = rememberNavController()
    var currentOrderId: OrderId? by remember(startDestination) {
        mutableStateOf((startDestination as? PosCartDestination.Checkout)?.orderId)
    }
    val startRoute: String = when (startDestination) {
        is PosCartDestination.Cart -> "cart"
        is PosCartDestination.Checkout -> "checkout"
    }

    NavHost(navController = navController, startDestination = startRoute) {
        composable("cart") {
            CartDestination(
                modifier = modifier,
                onCheckout = {
                    currentOrderId = OrderId("ORD-123")
                    navController.navigate("checkout")
                }
            )
        }
        composable("checkout") {
            val orderId = currentOrderId ?: OrderId("")
            CheckoutDestination(
                orderId = orderId,
                modifier = modifier,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
