package com.gpcasiapac.gpchelloworldkmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    // Koin is now initialized in the Compose layer via KoinMultiplatformApplication
    Window(
        onCloseRequest = ::exitApplication,
        title = "GPCHelloWorldKMP",
    ) {
        App()
    }
}