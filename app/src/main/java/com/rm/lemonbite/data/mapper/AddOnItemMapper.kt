package com.rm.lemonbite.data.mapper

import com.rm.lemonbite.data.remote.model.AddOnDto
import com.rm.lemonbite.domain.models.AddOnItem

fun AddOnDto.toDomain(): AddOnItem = AddOnItem(
    name = name,
    price = price,
)
