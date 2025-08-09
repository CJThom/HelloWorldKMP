package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.CartFeatureEntry
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.OrderId
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.PosCartDestination

private object CartNav {
    const val CART = "cart"
    const val ORDER_ID = "orderId"
    const val CHECKOUT_PATTERN = "checkout/{${ORDER_ID}}"

    fun routeOf(dest: PosCartDestination): String = when (dest) {
        is PosCartDestination.Cart -> CART
        is PosCartDestination.Checkout -> checkout(dest.orderId)
    }

    fun checkout(orderId: OrderId): String = "checkout/${'$'}{orderId.value}"
}

class CartFeatureEntryImpl : CartFeatureEntry {

    @Composable
    override fun Graph(
        modifier: Modifier,
        startDestination: PosCartDestination
    ) {
        val navController = rememberNavController()
        val startRoute = CartNav.routeOf(startDestination)
        NavHost(navController = navController, startDestination = startRoute) {
            composable(CartNav.routeOf(PosCartDestination.Cart)) {
                Cart(
                    modifier = modifier,
                    onCheckout = { orderId ->
                        navController.navigate(CartNav.checkout(orderId))
                    }
                )
            }
            composable(CartNav.CHECKOUT_PATTERN) { backStackEntry ->
                val orderId = backStackEntry.arguments?.getString(CartNav.ORDER_ID).orEmpty()
                Checkout(
                    orderId = OrderId(orderId),
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