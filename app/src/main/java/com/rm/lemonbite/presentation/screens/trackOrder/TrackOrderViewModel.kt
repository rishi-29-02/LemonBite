package com.rm.lemonbite.presentation.screens.trackOrder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.lemonbite.data.mapper.toDomain
import com.rm.lemonbite.domain.models.OrderDetails
import com.rm.lemonbite.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TrackOrderViewModel
@Inject
constructor(private val orderRepository: OrderRepository) :
    ViewModel() {
    private val _orderList = MutableStateFlow<List<OrderDetails>>(emptyList())
    val orderList = _orderList.asStateFlow()

    init {
        getOrderDetails()
    }

    fun getOrderDetails() {
        viewModelScope.launch {
            val orderDetails = orderRepository.getOrderList().map { it.toDomain() }
            _orderList.value = orderDetails
        }
    }
}
