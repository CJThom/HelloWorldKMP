package com.gpcasiapac.gpchelloworldkmp.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource.HelloNetworkDataSource
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource.HelloNetworkDataSourceImpl
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.repository.HelloRepositoryImpl
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository.HelloRepository
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.GetHelloMessageUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.GetRandomGreetingUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.usecase.GetSessionIdsFlowUseCase
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.HelloViewModel

val helloModule = module {
    // Data Sources
    single<HelloNetworkDataSource> { HelloNetworkDataSourceImpl() }
    
    // Repositories
    single<HelloRepository> { HelloRepositoryImpl(get()) }
    
    // Use Cases
    single { GetHelloMessageUseCase(get()) }
    single { GetRandomGreetingUseCase(get()) }
    single { GetSessionIdsFlowUseCase() }
    
    // ViewModels
    viewModel { HelloViewModel(get(), get(), get()) }
}