package com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginViewModel

val loginPresentationModule = module {
    // ViewModels
    viewModel { LoginViewModel(get()) }
}