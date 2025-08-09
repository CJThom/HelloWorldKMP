package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl.data

import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.model.Cart
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.model.CartItem
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.repository.CartRepository
import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryCartRepository : CartRepository {

    private val carts = mutableMapOf<String, MutableStateFlow<Cart>>()

    private fun getOrCreateCart(cartId: String): MutableStateFlow<Cart> {
        return carts.getOrPut(cartId) {
            MutableStateFlow(Cart(id = cartId, cartItemList = emptyList(), total = 0.0))
        }
    }

    override suspend fun addItemToCart(cartId: String, item: CartItem): DataResult<Cart> {
        val flow = getOrCreateCart(cartId)
        val current = flow.value
        val updatedItems = current.cartItemList + item
        val updated = current.copy(
            cartItemList = updatedItems,
            total = updatedItems.sumOf { it.price }
        )
        flow.value = updated
        return DataResult.Success(updated)
    }

    override suspend fun removeItemFromCart(cartId: String, itemId: String): DataResult<Cart> {
        val flow = getOrCreateCart(cartId)
        val current = flow.value
        val updatedItems = current.cartItemList.filterNot { it.id == itemId }
        val updated = current.copy(
            cartItemList = updatedItems,
            total = updatedItems.sumOf { it.price }
        )
        flow.value = updated
        return DataResult.Success(updated)
    }

    override fun observeCart(cartId: String): Flow<Cart> {
        return getOrCreateCart(cartId).asStateFlow()
    }
}