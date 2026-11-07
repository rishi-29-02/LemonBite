package com.rm.lemonbite.di

import com.rm.lemonbite.data.local.room.repository.CartRepositoryImpl
import com.rm.lemonbite.data.remote.MenuRepositoryImpl
import com.rm.lemonbite.data.local.room.repository.OrderRepositoryImpl
import com.rm.lemonbite.domain.repository.CartRepository
import com.rm.lemonbite.domain.repository.MenuRepository
import com.rm.lemonbite.domain.repository.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppRepoModule {

    @Binds
    @Singleton
    abstract fun bindMenuRepository(menuRepositoryImpl: MenuRepositoryImpl): MenuRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository
}