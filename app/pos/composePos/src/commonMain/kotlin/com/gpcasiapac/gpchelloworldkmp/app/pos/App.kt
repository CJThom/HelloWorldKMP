package com.gpcasiapac.gpchelloworldkmp.app.pos

import com.gpcasiapac.gpchelloworldkmp.common.presentation.theme.GPCTheme
import androidx.compose.runtime.Composable
import com.gpcasiapac.gpchelloworldkmp.app.pos.di.appKoinConfiguration
import com.gpcasiapac.gpchelloworldkmp.app.pos.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    // Clean KMP Koin setup using KoinMultiplatformApplication
    // This automatically handles Android context and platform-specific configuration
    KoinMultiplatformApplication(
        config = appKoinConfiguration,
        logLevel = Level.INFO
    ) {
        GPCTheme {
            AppNavigation()
        }
    }
}
