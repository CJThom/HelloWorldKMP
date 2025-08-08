package com.gpcasiapac.gpchelloworldkmp.feature.login.impl.di

import org.koin.dsl.module
import com.gpcasiapac.gpchelloworldkmp.feature.login.api.LoginFeatureEntry
import com.gpcasiapac.gpchelloworldkmp.feature.login.impl.LoginFeatureEntryImpl

val loginImplModule = module {
    single<LoginFeatureEntry> { LoginFeatureEntryImpl() }
}
