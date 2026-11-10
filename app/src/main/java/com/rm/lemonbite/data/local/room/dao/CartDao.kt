package com.rm.lemonbite.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rm.lemonbite.data.local.room.model.CartEntity

@Dao
interface CartDao {
    @Query("SELECT * FROM CartEntity LIMIT 1")
    suspend fun getCart(): CartEntity?

    @Query("SELECT * FROM CartEntity")
    suspend fun getAllItems(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCart(cart: CartEntity)

    @Query("DELETE FROM CartEntity")
    suspend fun clearCart()
}
