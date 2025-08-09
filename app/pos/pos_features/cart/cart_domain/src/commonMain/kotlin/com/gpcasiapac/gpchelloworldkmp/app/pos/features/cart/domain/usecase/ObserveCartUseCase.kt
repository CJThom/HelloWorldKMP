package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.model.Cart
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class ObserveCartUseCase(
    private val cartRepository: CartRepository
) {
    operator fun invoke(cartId: String): Flow<Cart> = cartRepository.observeCart(cartId)
}
