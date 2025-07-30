package com.gpcasiapac.gpchelloworldkmp.feature.hello.presentation

import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewEvent

sealed interface HelloEvent : ViewEvent {
    data class UpdateName(val name: String) : HelloEvent
    data object GenerateMessage : HelloEvent
    data object GenerateRandomGreeting : HelloEvent
    data object ClearMessage : HelloEvent
}