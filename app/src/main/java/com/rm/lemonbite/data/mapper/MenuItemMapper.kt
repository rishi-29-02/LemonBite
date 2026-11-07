package com.rm.lemonbite.data.mapper

import com.rm.lemonbite.data.remote.model.MenuItemDto
import com.rm.lemonbite.domain.models.MenuItem

fun MenuItemDto.toDomain() : MenuItem {
    return MenuItem(
        id = id,
        title = title,
        description = description,
        price = price,
        image = image,
        category = category,
        addons = addons.map { it.toDomain() }
    )
}