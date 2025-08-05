package com.gpcasiapac.gpchelloworldkmp.app.pickup.features.orders.domain.usecase

import com.gpcasiapac.gpchelloworldkmp.app.pickup.features.orders.domain.repository.OrderRepository
import com.gpcasiapac.gpchelloworldkmp.app.pickup.features.orders.domain.model.Order
import kotlinx.coroutines.flow.Flow

class GetOrdersUseCase(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(): Flow<List<Order>> {
        return orderRepository.observeOrders()
    }
}
