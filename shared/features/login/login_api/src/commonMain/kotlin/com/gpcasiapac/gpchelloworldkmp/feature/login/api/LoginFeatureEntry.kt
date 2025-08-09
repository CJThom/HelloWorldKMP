package com.gpcasiapac.gpchelloworldkmp.feature.login.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface LoginFeatureEntry {
    // Single-screen entry kept for compatibility
    @Composable
    fun Screen(onLoggedIn: () -> Unit = {})

    // Feature-level graph entry so the feature can own its internal navigation
    @Composable
    fun Graph(
        modifier: Modifier = Modifier,
        onLoggedIn: () -> Unit = {}
    )
}
