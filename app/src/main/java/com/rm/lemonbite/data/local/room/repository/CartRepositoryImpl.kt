package com.rm.lemonbite.data.local.room.repository

import android.util.Log
import com.rm.lemonbite.data.local.room.dao.CartDao
import com.rm.lemonbite.data.local.room.model.CartEntity
import com.rm.lemonbite.domain.models.MenuItem
import com.rm.lemonbite.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override suspend fun insertItemToCart(menuItem: MenuItem) {
        val currentCart = cartDao.getCart() ?: CartEntity(id = 1, items = emptyList(), totalPrice = 0.0)

        val existingItems = currentCart.items.toMutableList()
        val existingIndex = existingItems.indexOfFirst { it.id == menuItem.id }

        if (existingIndex != -1) {
            val existingItem = existingItems[existingIndex]
            existingItems[existingIndex] = existingItem.copy(
                addons = existingItem.addons.filter { it.isSelected }
            )
        } else {
            var itemToAdd = menuItem
            itemToAdd = itemToAdd.copy(
                addons = itemToAdd.addons.filter { it.isSelected }
            )
            existingItems.add(itemToAdd)
        }

        val updatedTotalPrice = existingItems.sumOf { item -> item.totalItemPrice }

        Log.d("items: ", existingItems.toString())

        val updatedCart = currentCart.copy(
            items = existingItems,
            totalPrice = updatedTotalPrice
        )

        cartDao.insertOrUpdateCart(updatedCart)
    }

    override suspend fun getCart(): CartEntity? {
        return cartDao.getCart()
    }

    override suspend fun reduceCartItemCount(menuItem: MenuItem, increment: Boolean) {
        val currentCart = cartDao.getCart() ?: return

        val existingItems = currentCart.items.toMutableList()
        val existingIdx = existingItems.indexOfFirst { it.id == menuItem.id}

        if (existingIdx != -1) {
            val existingItem = existingItems[existingIdx]
            existingItems[existingIdx] = existingItems[existingIdx].copy(
                qtySelected =  if (increment) existingItem.qtySelected + 1 else existingItem.qtySelected - 1,
            )
            existingItems[existingIdx] = existingItems[existingIdx].copy(
                totalItemPrice = getTotalItemPrice(existingItems[existingIdx])
            )
        }

        val updatedTotalPrice = existingItems.sumOf { item -> item.totalItemPrice }

        val updatedCart = currentCart.copy(
            items = existingItems,
            totalPrice = updatedTotalPrice
        )

        cartDao.insertOrUpdateCart(updatedCart)
    }

    private fun getTotalItemPrice(item: MenuItem) : Double {
        val addonsPrice = item.addons.filter { it.isSelected }.sumOf { it.price }
        return (item.price + addonsPrice) * item.qtySelected
    }

    override suspend fun removeCartItem(menuItem: MenuItem) {
        val currentCart = cartDao.getCart() ?: return

        val existingItems = currentCart.items.toMutableList().filter { it.id != menuItem.id }
        val updatedTotalPrice = existingItems.sumOf { item -> item.totalItemPrice }

        val updatedCart = currentCart.copy(
            items = existingItems,
            totalPrice = updatedTotalPrice
        )
        cartDao.insertOrUpdateCart(updatedCart)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}