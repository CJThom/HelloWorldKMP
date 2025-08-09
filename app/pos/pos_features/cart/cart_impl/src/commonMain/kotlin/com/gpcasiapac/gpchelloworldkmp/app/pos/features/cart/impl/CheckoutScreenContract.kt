package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl

import androidx.compose.runtime.Immutable
import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewEvent
import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewSideEffect
import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewState
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.OrderId

object CheckoutScreenContract {

    @Immutable
    data class State(
        val orderId: OrderId
    ) : ViewState

    sealed interface Event : ViewEvent {
        data object Back : Event
    }

    sealed interface Effect : ViewSideEffect {
        data object None : Effect
    }
}
