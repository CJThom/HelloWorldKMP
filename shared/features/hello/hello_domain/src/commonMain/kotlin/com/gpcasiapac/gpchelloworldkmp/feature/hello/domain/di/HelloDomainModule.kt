package com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.di

import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.GetHelloMessageUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.GetRandomGreetingUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.GetSessionIdsFlowUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.ObservePeriodicGreetingsUseCase

val helloDomainModule = module {
    // Use Cases
    single { GetHelloMessageUseCase(get()) }
    single { GetRandomGreetingUseCase(get()) }
    single { GetSessionIdsFlowUseCase() }
    single { ObservePeriodicGreetingsUseCase() }
}