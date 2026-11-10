package com.rm.lemonbite.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class AddOnItem(
    val name: String = "",
    val price: Double = 0.0,
    var isSelected: Boolean = false,
)
