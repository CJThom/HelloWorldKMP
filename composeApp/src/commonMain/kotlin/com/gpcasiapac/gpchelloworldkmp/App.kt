package com.gpcasiapac.gpchelloworldkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.di.helloDataModule
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.di.helloDomainModule
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.di.helloPresentationModule
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen.HelloDestination

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(helloDataModule, helloDomainModule, helloPresentationModule)
    }) {
        MaterialTheme {
            HelloDestination()
        }
    }
}