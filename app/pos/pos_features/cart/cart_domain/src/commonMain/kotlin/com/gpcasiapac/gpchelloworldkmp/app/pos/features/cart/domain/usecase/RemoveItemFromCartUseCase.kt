package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.repository.CartRepository
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.model.Cart
import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult

class RemoveItemFromCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartId: String, itemId: String): DataResult<Cart> {
        return cartRepository.removeItemFromCart(cartId, itemId)
    }
}
