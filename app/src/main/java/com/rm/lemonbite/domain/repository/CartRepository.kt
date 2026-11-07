package com.rm.lemonbite.domain.repository

import com.rm.lemonbite.data.local.room.model.CartEntity
import com.rm.lemonbite.domain.models.MenuItem

interface CartRepository {
    suspend fun insertItemToCart(menuItem: MenuItem)
    suspend fun getCart(): CartEntity?
    suspend fun reduceCartItemCount(menuItem: MenuItem, increment: Boolean)
    suspend fun removeCartItem(menuItem: MenuItem)
    suspend fun clearCart()
}