package com.gpcasiapac.gpchelloworldkmp.app.pos

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level
import com.gpcasiapac.gpchelloworldkmp.app.pos.di.appKoinConfiguration
import com.gpcasiapac.gpchelloworldkmp.app.pos.navigation.AndroidAppNavigation

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AndroidApp() {
    // Android-specific app using Navigation 3 ready navigation system
    // This automatically handles Android context and platform-specific configuration
    KoinMultiplatformApplication(
        config = appKoinConfiguration,
        logLevel = Level.INFO
    ) {
        MaterialTheme {
            AndroidAppNavigation()
        }
    }
}