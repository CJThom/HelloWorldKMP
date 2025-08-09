package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.PosCartDestination
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.OrderId
import kotlinx.serialization.Serializable

@Serializable
private sealed interface CartRoutes : NavKey {
    @Serializable data object Cart : CartRoutes
    @Serializable data class Checkout(val orderId: String) : CartRoutes
}

@Composable
actual fun CartGraphContent(
    modifier: Modifier,
    startDestination: PosCartDestination,
) {
    val initial = when (startDestination) {
        is PosCartDestination.Cart -> CartRoutes.Cart
        is PosCartDestination.Checkout -> CartRoutes.Checkout(startDestination.orderId.value)
    }
    val backStack = rememberNavBackStack<CartRoutes>(initial)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<CartRoutes.Cart> {
                CartDestination(
                    modifier = modifier,
                    onCheckout = {
                        backStack.add(CartRoutes.Checkout("ORD-123"))
                    }
                )
            }
            entry<CartRoutes.Checkout> { route ->
                CheckoutDestination(
                    orderId = OrderId(route.orderId),
                    modifier = modifier,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
