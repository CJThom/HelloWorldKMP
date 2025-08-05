package com.gpcasiapac.gpchelloworldkmp.app.pickup.features.orders.domain.repository

import com.gpcasiapac.gpchelloworldkmp.common.domain.DataResult
import com.gpcasiapac.gpchelloworldkmp.app.pickup.features.orders.domain.model.Order
import com.gpcasiapac.gpchelloworldkmp.app.pickup.features.orders.domain.model.OrderStatus
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun addOrder(order: Order): DataResult<Order>
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus): DataResult<Order>
    fun observeOrders(): Flow<List<Order>>
}
