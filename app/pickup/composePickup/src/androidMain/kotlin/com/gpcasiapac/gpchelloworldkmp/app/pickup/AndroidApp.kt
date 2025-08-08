package com.gpcasiapac.gpchelloworldkmp.app.pickup

import com.gpcasiapac.gpchelloworldkmp.common.presentation.theme.GPCTheme
import androidx.compose.runtime.Composable
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level
import com.gpcasiapac.gpchelloworldkmp.app.pickup.di.appKoinConfiguration
import com.gpcasiapac.gpchelloworldkmp.app.pickup.navigation.AndroidAppNavigation

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AndroidApp() {
    // Android-specific app using Navigation 3 ready navigation system
    // This automatically handles Android context and platform-specific configuration
    KoinMultiplatformApplication(
        config = appKoinConfiguration,
        logLevel = Level.INFO
    ) {
        GPCTheme {
            AndroidAppNavigation()
        }
    }
}