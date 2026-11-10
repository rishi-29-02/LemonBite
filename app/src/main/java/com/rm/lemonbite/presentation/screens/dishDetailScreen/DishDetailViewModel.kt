package com.rm.lemonbite.presentation.screens.dishDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.rm.lemonbite.domain.models.AddOnItem
import com.rm.lemonbite.domain.models.MenuItem
import com.rm.lemonbite.domain.repository.CartRepository
import com.rm.lemonbite.domain.repository.MenuRepository
import com.rm.lemonbite.presentation.navigation.NavRoutes
import com.rm.lemonbite.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DishDetailViewModel
@Inject
constructor(
    private val menuRepository: MenuRepository,
    private val cartRepository: CartRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val menuItemId = savedStateHandle.toRoute<NavRoutes.DishDetailsScreen>().itemId

    private val _itemUIState = MutableStateFlow<UiState<MenuItem>>(UiState.Loading)
    val itemUIState = _itemUIState.asStateFlow()

    private var _totalAmount = MutableStateFlow(0.0)
    val totalAmount = _totalAmount.asStateFlow()

    init {
        getDishDetails(menuItemId)
    }

    private fun getDishDetails(id: Int) {
        viewModelScope.launch {
            menuRepository
                .getMenuById(id)
                .onSuccess { menuItem ->
                    _totalAmount.value = menuItem.price
                    _itemUIState.value = UiState.Success(menuItem)
                }.onFailure { exception ->
                    _itemUIState.value =
                        UiState.Error(exception.message ?: "An unexpected error occurred")
                }
        }
    }

    fun updateAddOns(addOnItem: AddOnItem, checked: Boolean) {
        if (checked) {
            _totalAmount.value += addOnItem.price
        } else {
            _totalAmount.value -= addOnItem.price
        }

        (_itemUIState.value as? UiState.Success)?.data?.addons?.forEach { addOn ->
            if (addOn.name == addOnItem.name) {
                addOn.isSelected = checked
            }
        }
    }

    fun addQty() {
        (_itemUIState.value as? UiState.Success)?.data?.qtySelected += 1
        (_itemUIState.value as? UiState.Success)?.data?.price?.let { price ->
            _totalAmount.value += price
        }
    }

    fun removeQty() {
        (_itemUIState.value as? UiState.Success)?.data?.qtySelected?.let { qty ->
            if (qty > 1) {
                (_itemUIState.value as? UiState.Success)?.data?.qtySelected -= 1
                (_itemUIState.value as? UiState.Success)?.data?.price?.let { price ->
                    _totalAmount.value -= price
                }
            }
        }
    }

    fun addToCart(addToCart: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            (_itemUIState.value as? UiState.Success)?.data?.let { menuItem ->
                menuItem.totalItemPrice = _totalAmount.value
            }
            cartRepository.insertItemToCart(
                menuItem = (_itemUIState.value as? UiState.Success)?.data!!,
            )
        }
        addToCart()
    }
}
