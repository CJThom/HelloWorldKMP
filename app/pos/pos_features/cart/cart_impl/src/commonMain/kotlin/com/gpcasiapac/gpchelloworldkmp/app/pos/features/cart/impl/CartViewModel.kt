package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl

import androidx.lifecycle.viewModelScope
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.OrderId
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.model.CartItem
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.usecase.AddToCartUseCase
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.usecase.ObserveCartUseCase
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.usecase.RemoveItemFromCartUseCase
import com.gpcasiapac.gpchelloworldkmp.common.presentation.MVIViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random

class CartViewModel(
    private val addToCart: AddToCartUseCase,
    private val removeFromCart: RemoveItemFromCartUseCase,
    private val observeCart: ObserveCartUseCase,
) : MVIViewModel<CartScreenContract.Event, CartScreenContract.State, CartScreenContract.Effect>() {

    companion object { private const val DEFAULT_CART_ID = "default_cart" }

    private var observeJob: Job? = null

    override fun setInitialState(): CartScreenContract.State =
        CartScreenContract.State.initial(DEFAULT_CART_ID)

    override fun onStart() {
        // Start observing cart
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            observeCart(viewState.value.cartId).collectLatest { cart ->
                setState { copy(cart = cart, isLoading = false, error = null) }
            }
        }
    }

    override fun handleEvents(event: CartScreenContract.Event) {
        when (event) {
            CartScreenContract.Event.Load -> refreshCart()
            CartScreenContract.Event.AddSampleItem -> addSampleItem()
            is CartScreenContract.Event.RemoveItem -> removeItem(event.itemId)
            CartScreenContract.Event.CheckoutClicked -> navigateToCheckout()
            CartScreenContract.Event.ClearError -> clearError()
        }
    }

    private fun refreshCart() {
        // observe already active; could trigger any refresh logic if needed
    }

    private fun addSampleItem() {
        viewModelScope.launch {
            val id = Random.nextInt(1000, 9999).toString()
            val price = Random.nextDouble(5.0, 50.0)
            val item = CartItem(id = id, description = "Item $id", price = price)
            val result = addToCart(viewState.value.cartId, item)
            if (result is com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult.Error) {
                setEffect { CartScreenContract.Effect.ShowError("Failed to add item") }
            }
        }
    }

    private fun removeItem(itemId: String) {
        viewModelScope.launch {
            val result = removeFromCart(viewState.value.cartId, itemId)
            if (result is com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult.Error) {
                setEffect { CartScreenContract.Effect.ShowError("Failed to remove item") }
            }
        }
    }

    private fun navigateToCheckout() {
        val orderId = OrderId(value = "ORD-${'$'}{System.currentTimeMillis()}")
        setEffect { CartScreenContract.Effect.Navigation.Checkout(orderId) }
    }

    private fun clearError() { setState { copy(error = null) } }
}
