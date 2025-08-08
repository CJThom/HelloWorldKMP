package com.gpcasiapac.gpchelloworldkmp.feature.cart.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gpcasiapac.gpchelloworldkmp.feature.cart.api.CartFeatureEntry

class CartFeatureEntryImpl : CartFeatureEntry {
    override val route: String = "pos/cart"

    @Composable
    override fun Screen(modifier: Modifier, onCheckout: () -> Unit) {
        CartDestination(modifier = modifier, onCheckout = onCheckout)
    }
}
