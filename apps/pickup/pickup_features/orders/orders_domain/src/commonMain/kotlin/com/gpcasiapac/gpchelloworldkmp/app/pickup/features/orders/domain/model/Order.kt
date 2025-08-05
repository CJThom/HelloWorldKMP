package com.gpcasiapac.gpchelloworldkmp.app.pickup.features.orders.domain.model

data class Order(
    val id: String,
    val orderItemList: List<OrderItem>,
    val status: OrderStatus
)

data class OrderItem(
    val id: String,
    val description: String,
    val price: Double
)

enum class OrderStatus {
    PENDING,
    ASSIGNED,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED
}
