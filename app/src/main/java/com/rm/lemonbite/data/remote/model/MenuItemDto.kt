package com.rm.lemonbite.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuItemDto(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val category: String = "",
    val addons: List<AddOnDto> = emptyList()
)