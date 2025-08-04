package com.gpcasiapac.gpchelloworldkmp.feature.hello.data.di

import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource.HelloNetworkDataSource
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource.HelloNetworkDataSourceImpl
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource.MockHelloNetworkDataSourceImpl
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.repository.HelloRepositoryImpl
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository.HelloRepository
import com.gpcasiapac.gpchelloworldkmp.config.BuildConfig

val helloDataModule = module {
    // Data Sources - conditional based on build config
    single<HelloNetworkDataSource> { 
        if (BuildConfig.USE_MOCK_DATA) {
            MockHelloNetworkDataSourceImpl() // No dependencies needed - uses simple JSON loading
        } else {
            HelloNetworkDataSourceImpl(get()) // Inject HttpClient from common module
        }
    }
    
    // Repositories
    single<HelloRepository> { HelloRepositoryImpl(get()) }
}