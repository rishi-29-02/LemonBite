package com.rm.lemonbite.data.local.room.repository

import com.rm.lemonbite.data.local.room.dao.OrderItemDao
import com.rm.lemonbite.data.local.room.model.OrderEntity
import com.rm.lemonbite.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderItemDao: OrderItemDao
) : OrderRepository{
    override suspend fun insertOrderList(order: OrderEntity) {
        orderItemDao.insertOrderItem(order)
    }

    override suspend fun getOrderList(): List<OrderEntity> {
        return orderItemDao.getAllOrders()
    }
}