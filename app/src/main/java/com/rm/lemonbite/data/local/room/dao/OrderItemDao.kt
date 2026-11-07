package com.rm.lemonbite.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rm.lemonbite.data.local.room.model.OrderEntity

@Dao
interface OrderItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(orderItems: OrderEntity)

    @Query("SELECT * FROM OrderEntity")
    suspend fun getAllOrders(): List<OrderEntity>
}