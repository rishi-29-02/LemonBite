package com.rm.lemonbite.presentation.screens.orderSummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.lemonbite.data.local.room.model.OrderEntity
import com.rm.lemonbite.domain.models.MenuItem
import com.rm.lemonbite.domain.repository.CartRepository
import com.rm.lemonbite.domain.repository.MenuRepository
import com.rm.lemonbite.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class OrderSummaryViewModel
@Inject
constructor(
    private val menuRepository: MenuRepository,
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
) : ViewModel() {
    private val _cartItemDetails = MutableStateFlow<List<MenuItem>>(emptyList())
    val cartItemDetails = _cartItemDetails.asStateFlow()

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val manuItem = _menuItems.asStateFlow()

    private var _totalPrice = MutableStateFlow(0.0)
    val totalPrice = _totalPrice.asStateFlow()

    init {
        getCartDetails()
        getMenuItems()
    }

    fun getCartDetails() {
        viewModelScope.launch {
            val cartDetails = cartRepository.getCart()
            if (cartDetails != null) {
                _cartItemDetails.value = cartDetails.items
                _totalPrice.value = cartDetails.totalPrice
            }
        }
    }

    fun getMenuItems() {
        viewModelScope.launch {
            menuRepository.getMenu().onSuccess {
                _menuItems.value = it
            }
        }
    }

    fun updateCartItemCount(menuItem: MenuItem, increment: Boolean) {
        viewModelScope.launch {
            cartRepository.reduceCartItemCount(menuItem, increment)
            getCartDetails()
        }
    }

    fun removeCartItem(item: MenuItem) {
        viewModelScope.launch {
            cartRepository.removeCartItem(item)
            getCartDetails()
        }
    }

    fun placeOrder(onOrderPlaced: () -> Unit) {
        viewModelScope.launch {
            orderRepository.insertOrderList(
                OrderEntity(
                    orderedItems = _cartItemDetails.value,
                    totalPrice = _totalPrice.value,
                ),
            )
            cartRepository.clearCart()
            onOrderPlaced()
        }
    }
}
