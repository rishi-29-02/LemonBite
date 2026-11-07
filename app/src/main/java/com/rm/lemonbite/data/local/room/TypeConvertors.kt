package com.rm.lemonbite.data.local.room

import androidx.room.TypeConverter
import com.rm.lemonbite.domain.models.MenuItem
import kotlinx.serialization.json.Json

class TypeConvertors {
    @TypeConverter
    fun fromMenuItemList(menuItems: List<MenuItem>): String {
        return Json.encodeToString(menuItems)
    }

    @TypeConverter
    fun toMenuItemList(value: String): List<MenuItem> {
        return Json.decodeFromString(value)
    }
}