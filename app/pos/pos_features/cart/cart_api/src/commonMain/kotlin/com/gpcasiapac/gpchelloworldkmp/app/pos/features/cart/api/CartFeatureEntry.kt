package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Type-safe destination contract for POS Cart feature

data class OrderId(val value: String)

sealed interface PosCartDestination {
    data object Cart : PosCartDestination
    data class Checkout(val orderId: OrderId) : PosCartDestination
}

interface CartFeatureEntry {
    // Preferred entry: feature-local navigation graph
    @Composable
    fun Graph(
        modifier: Modifier = Modifier,
        startDestination: PosCartDestination = PosCartDestination.Cart,
    )

    // Optional convenience: direct per-screen entry points (may be implemented as simple delegates)
    @Composable
    fun Cart(modifier: Modifier = Modifier, onCheckout: (OrderId) -> Unit = {})

    @Composable
    fun Checkout(orderId: OrderId, modifier: Modifier = Modifier, onBack: () -> Unit = {})
}