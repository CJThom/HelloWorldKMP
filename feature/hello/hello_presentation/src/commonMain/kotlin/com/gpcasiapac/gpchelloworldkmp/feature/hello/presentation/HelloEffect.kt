package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation

import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewSideEffect

sealed interface HelloEffect : ViewSideEffect {
    data class ShowToast(val message: String) : HelloEffect
    data class ShowError(val error: String) : HelloEffect
}