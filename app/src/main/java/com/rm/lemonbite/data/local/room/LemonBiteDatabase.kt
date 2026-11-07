package com.rm.lemonbite.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rm.lemonbite.data.local.room.dao.CartDao
import com.rm.lemonbite.data.local.room.dao.OrderItemDao
import com.rm.lemonbite.data.local.room.model.CartEntity
import com.rm.lemonbite.data.local.room.model.OrderEntity

@Database(entities = [OrderEntity::class, CartEntity::class], version = 1)
@TypeConverters(TypeConvertors::class)
abstract class LemonBiteDatabase: RoomDatabase() {
    abstract fun orderItemDao(): OrderItemDao

    abstract fun cartDao(): CartDao
}