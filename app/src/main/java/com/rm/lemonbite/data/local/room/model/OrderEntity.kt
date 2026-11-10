package com.rm.lemonbite.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rm.lemonbite.domain.models.MenuItem

@Entity
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val orderedItems: List<MenuItem> = emptyList(),
    val totalPrice: Double = 0.0,
)
