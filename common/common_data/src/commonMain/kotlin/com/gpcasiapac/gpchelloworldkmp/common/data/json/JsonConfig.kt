package com.gpcasiapac.gpchelloworldkmp.common.data.json

import kotlinx.serialization.json.Json

/**
 * Common Json configuration used across the application.
 * This ensures consistent JSON serialization/deserialization behavior
 * between network requests (Ktor) and resource loading.
 */
object JsonConfig {
    
    /**
     * Shared Json instance with common configuration.
     * Used by both HTTP client (Ktor) and resource loading.
     */
    val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
}