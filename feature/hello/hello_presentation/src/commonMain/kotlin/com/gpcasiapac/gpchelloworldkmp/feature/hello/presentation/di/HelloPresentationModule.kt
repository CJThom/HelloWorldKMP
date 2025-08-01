package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.hello_screen.HelloViewModel

val helloPresentationModule = module {
    // ViewModels
    viewModel { HelloViewModel(get(), get(), get()) }
}