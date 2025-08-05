package com.gpcasiapac.gpchelloworldkmp.feature.login.data.di

import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.login.data.datasource.LoginNetworkDataSource
import com.gpcasiapac.gpchelloworldkmp.feature.login.data.datasource.MockLoginNetworkDataSourceImpl
import com.gpcasiapac.gpchelloworldkmp.feature.login.data.repository.LoginRepositoryImpl
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.repository.LoginRepository

val loginDataModule = module {
    // Data Sources - using mock implementation for now
    // TODO: Add real LoginNetworkDataSourceImpl when API is available
    single<LoginNetworkDataSource> { 
        MockLoginNetworkDataSourceImpl() // No dependencies needed for mock
    }
    
    // Repositories
    single<LoginRepository> { LoginRepositoryImpl(get()) }
}