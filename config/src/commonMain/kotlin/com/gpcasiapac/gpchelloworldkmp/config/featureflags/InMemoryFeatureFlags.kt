package com.gpcasiapac.gpchelloworldkmp.config.featureflags

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Simple in-memory FeatureFlags implementation suitable for dev/testing.
 * Can be replaced later with a remote-config backed implementation.
 */
class InMemoryFeatureFlags(initial: Map<String, Any?> = emptyMap()) : FeatureFlags {
    private val state = MutableStateFlow(initial)

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: FlagKey<T>): T = (state.value[key.name] ?: key.default) as T

    @Suppress("UNCHECKED_CAST")
    override fun <T> observe(key: FlagKey<T>): Flow<T> =
        state.asStateFlow().map { (it[key.name] ?: key.default) as T }.distinctUntilChanged()

    fun update(values: Map<String, Any?>) { state.value = state.value + values }
    fun set(key: String, value: Any?) { state.value = state.value + (key to value) }
}