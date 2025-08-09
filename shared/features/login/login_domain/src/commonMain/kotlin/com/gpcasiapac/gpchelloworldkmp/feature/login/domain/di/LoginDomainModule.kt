package com.gpcasiapac.gpchelloworldkmp.feature.login.domain.di

import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.LoginUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.LogoutUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.GetCurrentUserUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.usecase.IsLoggedInUseCase

val loginDomainModule = module {
    // Use Cases
    single { LoginUseCase(get()) }
    single { LogoutUseCase(get()) }
    single { GetCurrentUserUseCase(get()) }
    single { IsLoggedInUseCase(get()) }
}