package com.gpcasiapac.gpchelloworldkmp.feature.login.impl.di

import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.login.api.LoginFeatureEntry
import com.gpcasiapac.gpchelloworldkmp.feature.login.impl.LoginFeatureEntryImpl
import com.gpcasiapac.gpchelloworldkmp.feature.login.presentation.login_screen.LoginViewModel
import com.gpcasiapac.gpchelloworldkmp.feature.login.data.datasource.LoginNetworkDataSource
import com.gpcasiapac.gpchelloworldkmp.feature.login.data.datasource.MockLoginNetworkDataSourceImpl
import com.gpcasiapac.gpchelloworldkmp.feature.login.data.repository.LoginRepositoryImpl
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.repository.LoginRepository
import org.koin.core.module.dsl.viewModel

val loginImplModule = module {
    // Data layer bindings consolidated into impl
    single<LoginNetworkDataSource> { MockLoginNetworkDataSourceImpl() }
    single<LoginRepository> { LoginRepositoryImpl(get()) }

    // Presentation layer binding consolidated into impl
    viewModel { LoginViewModel(get()) }

    // Feature entry
    single<LoginFeatureEntry> { LoginFeatureEntryImpl() }
}
