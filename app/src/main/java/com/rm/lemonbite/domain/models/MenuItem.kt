package com.rm.lemonbite.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MenuItem(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val category: String = "",
    val addons: List<AddOnItem> = emptyList(),
    var qtySelected: Int = 1,
    var totalItemPrice: Double = 0.0
)


