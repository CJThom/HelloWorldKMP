package com.gpcasiapac.gpchelloworldkmp.config.featureflags.di

import com.gpcasiapac.gpchelloworldkmp.config.featureflags.FeatureFlags
import com.gpcasiapac.gpchelloworldkmp.config.featureflags.InMemoryFeatureFlags
import org.koin.dsl.module

val featureFlagsModule = module {
    // Backing store
    single { InMemoryFeatureFlags() }
    // Public interface
    single<FeatureFlags> { get<InMemoryFeatureFlags>() }
}