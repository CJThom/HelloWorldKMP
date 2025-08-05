package com.gpcasiapac.gpchelloworldkmp.feature.login.domain.di

import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.LoginUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.LogoutUseCase

val loginDomainModule = module {
    // Use Cases
    single { LoginUseCase(get()) }
    single { LogoutUseCase(get()) }
}