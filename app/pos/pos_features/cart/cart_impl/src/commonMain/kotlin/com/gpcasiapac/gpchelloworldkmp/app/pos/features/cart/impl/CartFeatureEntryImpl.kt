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
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.CartFeatureEntry
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.OrderId
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.PosCartDestination

private object CartRoutes {
    const val Cart = "cart"
    const val Checkout = "checkout"
}

class CartFeatureEntryImpl : CartFeatureEntry {

    @Composable
    override fun Graph(
        modifier: Modifier,
        startDestination: PosCartDestination
    ) {
        val navController = rememberNavController()
        var currentOrderId by remember(startDestination) {
            mutableStateOf(if (startDestination is PosCartDestination.Checkout) startDestination.orderId else null)
        }
        val startRoute = when (startDestination) {
            is PosCartDestination.Cart -> CartRoutes.Cart
            is PosCartDestination.Checkout -> CartRoutes.Checkout
        }
        NavHost(navController = navController, startDestination = startRoute) {
            composable(CartRoutes.Cart) {
                CartDestination(
                    modifier = modifier,
                    onCheckout = {
                        currentOrderId = OrderId("ORD-123")
                        navController.navigate(CartRoutes.Checkout)
                    }
                )
            }
            composable(CartRoutes.Checkout) {
                val orderId = currentOrderId ?: OrderId("")
                CheckoutDestination(
                    orderId = orderId,
                    modifier = modifier,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }

    @Composable
    override fun Cart(
        modifier: Modifier,
        onCheckout: (OrderId) -> Unit
    ) {
        CartDestination(modifier = modifier, onCheckout = { onCheckout(OrderId("ORD-123")) })
    }

    @Composable
    override fun Checkout(
        orderId: OrderId,
        modifier: Modifier,
        onBack: () -> Unit
    ) {
        CheckoutDestination(orderId = orderId, modifier = modifier, onBack = onBack)
    }
}