package com.gpcasiapac.gpchelloworldkmp.di

import org.koin.dsl.koinConfiguration
import com.gpcasiapac.gpchelloworldkmp.common.data.di.commonDataModules
import com.gpcasiapac.gpchelloworldkmp.feature.hello.data.di.helloDataModule
import com.gpcasiapac.gpchelloworldkmp.feature.hello.domain.di.helloDomainModule
import com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation.di.helloPresentationModule

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
    }
    
    modules(allModules)
}