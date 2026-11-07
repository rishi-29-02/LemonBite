package com.rm.lemonbite.domain.models

data class OrderDetails(
    val orderId: Int = 0,
    val orderedItems: List<MenuItem> = emptyList(),
    val totalPrice: Double = 0.0
)
