package com.gpcasiapac.gpchelloworldkmp.common.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import kotlinx.coroutines.FlowPreview

/**
 * GPC Material 3 Theme
 * 
 * This is the main theme composable that should be used by all apps in the project.
 * It provides a consistent Material 3 design system with custom GPC branding.
 * 
 * @param darkTheme Whether to use dark theme. Defaults to system setting.
 * @param content The content to be themed.
 */
@Composable
fun GPCTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {


    MaterialTheme(
        colorScheme = GPCLightColorScheme,
        typography = GPCTypography,
        shapes = GPCShapes,
        content = content
    )

}

