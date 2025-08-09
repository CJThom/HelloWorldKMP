package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.PosCartDestination


/**
 * Platform-specific Cart graph content.
 * - androidMain actual uses Navigation 3
 * - jvmMain actual uses navigation-compose
 */
@Composable
expect fun CartGraphContent(
    modifier: Modifier = Modifier,
    startDestination: PosCartDestination = PosCartDestination.Cart,
)
