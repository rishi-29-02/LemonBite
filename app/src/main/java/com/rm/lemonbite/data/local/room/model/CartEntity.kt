package com.rm.lemonbite.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rm.lemonbite.domain.models.MenuItem

@Entity
data class CartEntity(
    @PrimaryKey
    val id: Int = 1,
    val items: List<MenuItem> = emptyList(),
    val totalPrice: Double = 0.0
)
