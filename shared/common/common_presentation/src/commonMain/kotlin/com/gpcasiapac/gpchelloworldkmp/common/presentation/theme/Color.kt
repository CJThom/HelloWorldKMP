package com.gpcasiapac.gpchelloworldkmp.common.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Primary brand colors
val GPCPrimary = Color(0xFF1976D2)
val GPCPrimaryVariant = Color(0xFF1565C0)
val GPCSecondary = Color(0xFF03DAC6)
val GPCSecondaryVariant = Color(0xFF018786)

// Surface and background colors
val GPCBackground = Color(0xFFFFFBFE)
val GPCSurface = Color(0xFFFFFBFE)
val GPCError = Color(0xFFBA1A1A)

// On colors (text/icons on colored surfaces)
val GPCOnPrimary = Color(0xFFFFFFFF)
val GPCOnSecondary = Color(0xFF000000)
val GPCOnBackground = Color(0xFF1C1B1F)
val GPCOnSurface = Color(0xFF1C1B1F)
val GPCOnError = Color(0xFFFFFFFF)

val GPCLightColorScheme = lightColorScheme(
    primary = GPCPrimary,
    onPrimary = GPCOnPrimary,
    primaryContainer = GPCPrimaryVariant,
    onPrimaryContainer = GPCOnPrimary,
    secondary = GPCSecondary,
    onSecondary = GPCOnSecondary,
    secondaryContainer = GPCSecondaryVariant,
    onSecondaryContainer = GPCOnSecondary,
    tertiary = GPCSecondary,
    onTertiary = GPCOnSecondary,
    error = GPCError,
    onError = GPCOnError,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = GPCBackground,
    onBackground = GPCOnBackground,
    surface = GPCSurface,
    onSurface = GPCOnSurface,
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF313033),
    inverseOnSurface = Color(0xFFF4EFF4),
    inversePrimary = GPCOnPrimary,
    surfaceDim = Color(0xFFDDD8DD),
    surfaceBright = Color(0xFFFDF8FD),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF7F2F7),
    surfaceContainer = Color(0xFFF1ECF1),
    surfaceContainerHigh = Color(0xFFEBE6EB),
    surfaceContainerHighest = Color(0xFFE6E0E5)
)
