package com.gpcasiapac.gpchelloworldkmp.feature.hello.data.di

import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource.HelloNetworkDataSource
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.datasource.HelloNetworkDataSourceImpl
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.repository.HelloRepositoryImpl
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.repository.HelloRepository

val helloDataModule = module {
    // Data Sources
    single<HelloNetworkDataSource> { HelloNetworkDataSourceImpl() }
    
    // Repositories
    single<HelloRepository> { HelloRepositoryImpl(get()) }
}