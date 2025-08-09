package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl

import androidx.compose.runtime.Immutable
import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewEvent
import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewSideEffect
import com.gpcasiapac.gpchelloworldkmp.common.presentation.ViewState
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.OrderId
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.model.Cart

object CartScreenContract {

    @Immutable
    data class State(
        val cartId: String,
        val cart: Cart?,
        val isLoading: Boolean,
        val error: String?
    ) : ViewState {
        companion object {
            fun initial(cartId: String) = State(
                cartId = cartId,
                cart = null,
                isLoading = false,
                error = null
            )
        }
    }

    sealed interface Event : ViewEvent {
        data object Load : Event
        data object AddSampleItem : Event
        data class RemoveItem(val itemId: String) : Event
        data object CheckoutClicked : Event
        data object ClearError : Event
    }

    sealed interface Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect
        data class ShowError(val error: String) : Effect

        sealed interface Navigation : Effect {
            data class Checkout(val orderId: OrderId) : Navigation
        }
    }
}
