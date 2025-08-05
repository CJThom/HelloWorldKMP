package com.gpcasiapac.gpchelloworldkmp.common.data.json

import kotlinx.serialization.DeserializationStrategy

/**
 * Simple JSON resource loading that works like Ktor's response.body<T>()
 * but for JSON files. Uses the same JsonConfig.json for consistency.
 */

// Platform-specific resource loading - simple expect/actual pattern
expect suspend fun loadJsonResourceString(resourcePath: String): String
