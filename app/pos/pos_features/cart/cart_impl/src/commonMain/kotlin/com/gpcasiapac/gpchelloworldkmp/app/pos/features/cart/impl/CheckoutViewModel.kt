package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl

import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.OrderId
import com.gpcasiapac.gpchelloworldkmp.common.presentation.MVIViewModel

class CheckoutViewModel(
    private val initialOrderId: OrderId
) : MVIViewModel<CheckoutScreenContract.Event, CheckoutScreenContract.State, CheckoutScreenContract.Effect>() {

    override fun setInitialState(): CheckoutScreenContract.State =
        CheckoutScreenContract.State(orderId = initialOrderId)

    override fun onStart() {
        // no-op for now
    }

    override fun handleEvents(event: CheckoutScreenContract.Event) {
        when (event) {
            CheckoutScreenContract.Event.Back -> {
                // handled by destination via callback; no effect here
            }
        }
    }
}
