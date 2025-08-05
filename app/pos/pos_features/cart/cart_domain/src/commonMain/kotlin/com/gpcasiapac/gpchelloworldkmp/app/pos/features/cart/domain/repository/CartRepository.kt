package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.repository

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.model.Cart
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addItemToCart(cartId: String, item: CartItem): DataResult<Cart>
    suspend fun removeItemFromCart(cartId: String, itemId: String): DataResult<Cart>
    fun observeCart(cartId: String): Flow<Cart>
}
