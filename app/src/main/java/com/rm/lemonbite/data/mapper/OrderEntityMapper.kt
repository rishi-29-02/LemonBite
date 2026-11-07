package com.rm.lemonbite.data.mapper

import com.rm.lemonbite.data.local.room.model.OrderEntity
import com.rm.lemonbite.domain.models.OrderDetails


fun OrderEntity.toDomain() = OrderDetails(
    orderId = orderId,
    orderedItems = orderedItems,
    totalPrice = totalPrice
)