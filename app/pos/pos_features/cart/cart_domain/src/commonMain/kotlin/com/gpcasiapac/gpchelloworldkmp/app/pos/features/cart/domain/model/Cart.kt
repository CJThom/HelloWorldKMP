package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.domain.model

data class Cart(
    val id: String,
    val cartItemList: List<CartItem>,
    val total: Double
)

data class CartItem(
    val id: String,
    val description: String,
    val price: Double
)
