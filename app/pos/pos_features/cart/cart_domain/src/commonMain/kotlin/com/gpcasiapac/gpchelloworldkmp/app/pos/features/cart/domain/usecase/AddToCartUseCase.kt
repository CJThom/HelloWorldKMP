package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.repository.CartRepository
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.model.Cart
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.model.CartItem
import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult

class AddToCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartId: String, item: CartItem): DataResult<Cart> {
        return cartRepository.addItemToCart(cartId, item)
    }
}