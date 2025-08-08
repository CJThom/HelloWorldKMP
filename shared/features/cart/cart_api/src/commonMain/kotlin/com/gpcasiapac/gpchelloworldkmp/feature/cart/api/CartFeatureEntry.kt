package com.gpcasiapac.gpchelloworldkmp.feature.cart.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface CartFeatureEntry {
    val route: String
    @Composable
    fun Screen(modifier: Modifier = Modifier, onCheckout: () -> Unit = {})
}
