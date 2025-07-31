package com.gpcasiapac.gpchelloworldkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import com.gpcasiapac.gpchelloworldkmp.di.helloModule
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.HelloScreen

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(helloModule)
    }) {
        MaterialTheme {
            HelloScreen()
        }
    }
}