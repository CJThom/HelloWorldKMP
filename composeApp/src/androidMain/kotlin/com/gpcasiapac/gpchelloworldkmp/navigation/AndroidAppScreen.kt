package com.gpcasiapac.gpchelloworldkmp.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AndroidAppScreen : NavKey {
    @Serializable 
    data object Login : AndroidAppScreen
    
    @Serializable 
    data object MainApp : AndroidAppScreen
}