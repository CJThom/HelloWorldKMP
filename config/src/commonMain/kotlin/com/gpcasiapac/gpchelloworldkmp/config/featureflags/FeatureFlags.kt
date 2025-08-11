package com.gpcasiapac.gpchelloworldkmp.config.featureflags

import kotlinx.coroutines.flow.Flow

/**
 * Type-safe key for a feature flag value.
 */
interface FlagKey<T> {
    val name: String
    val default: T
}

/**
 * Read-only feature flags interface.
 */
interface FeatureFlags {
    fun <T> get(key: FlagKey<T>): T
    fun <T> observe(key: FlagKey<T>): Flow<T>

    fun isEnabled(key: FlagKey<Boolean>): Boolean = get(key)
}