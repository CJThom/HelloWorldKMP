package com.gpcasiapac.gpchelloworldkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen.HelloDestination
import com.gpcasiapac.gpchelloworldkmp.di.appKoinConfiguration

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
        MaterialTheme {
            HelloDestination()
        }
    }
}