package com.gpcasiapac.gpchelloworldkmp.di

import org.koin.dsl.koinConfiguration
import com.gpcasiapac.gpchelloworldkmp.common.data.di.commonDataModules
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.di.helloDataModule
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.di.helloDomainModule
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.di.helloPresentationModule
import com.gpcasiapac.gpchelloworldkmp.feature.login.domain.di.loginDomainModule
import com.gpcasiapac.gpchelloworldkmp.feature.login.impl.di.loginImplModule
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl.di.cartImplModule
import com.gpcasiapac.gpchelloworldkmp.config.featureflags.di.featureFlagsModule

/**
 * Clean KMP Koin configuration using the new koinConfiguration DSL
 * This configuration will be used by KoinMultiplatformApplication
 */
val appKoinConfiguration = koinConfiguration {
    // Build a comprehensive list of all modules for better readability
    val allModules = buildList {
        // Add common data modules (includes platform-specific HTTP clients)
        addAll(commonDataModules)
        
        // Add feature modules
        add(helloDataModule)
        add(helloDomainModule)
        add(helloPresentationModule)

        // Feature flags
        add(featureFlagsModule)
        
        // Add login modules (domain + impl only; data/presentation consolidated into impl)
        add(loginDomainModule)
        add(loginImplModule)
        
        // POS Cart feature impl
        add(cartImplModule)
    }
    
    modules(allModules)
}