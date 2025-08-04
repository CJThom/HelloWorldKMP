package com.gpcasiapac.gpchelloworldkmp.common.data.json

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Android implementation - loads JSON from raw resources
 * Uses the same pattern as the user's example with R.raw resources
 */
actual suspend fun loadJsonResourceString(resourcePath: String): String = withContext(Dispatchers.IO) {
    val context = AndroidResourceHelper.getContext()
    
    // Convert filename to resource name (remove extension)
    val resourceName = resourcePath.substringBeforeLast('.')
    val resourceId = context.resources.getIdentifier(resourceName, "raw", context.packageName)
    
    if (resourceId == 0) {
        throw IllegalArgumentException("Resource not found: $resourcePath")
    }
    
    context.resources.openRawResource(resourceId).bufferedReader().use { it.readText() }
}

/**
 * Helper to get Android Context through Koin DI
 */
private object AndroidResourceHelper : KoinComponent {
    fun getContext(): Context = inject<Context>().value
}