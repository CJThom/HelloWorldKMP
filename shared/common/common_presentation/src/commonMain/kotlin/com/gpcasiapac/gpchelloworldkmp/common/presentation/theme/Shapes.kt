package com.gpcasiapac.gpchelloworldkmp.common.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// GPC Shapes following Material 3 design system
val GPCShapes = Shapes(
    // Extra small components (chips, small buttons)
    extraSmall = RoundedCornerShape(4.dp),
    
    // Small components (buttons, text fields)
    small = RoundedCornerShape(8.dp),
    
    // Medium components (cards, dialogs)
    medium = RoundedCornerShape(12.dp),
    
    // Large components (bottom sheets, large cards)
    large = RoundedCornerShape(16.dp),
    
    // Extra large components (full screen dialogs)
    extraLarge = RoundedCornerShape(28.dp)
)