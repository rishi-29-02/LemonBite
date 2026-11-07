package com.rm.lemonbite.domain.repository

import com.rm.lemonbite.data.local.room.model.OrderEntity

interface OrderRepository {
    suspend fun getOrderList() : List<OrderEntity>
    suspend fun insertOrderList(order: OrderEntity)
}