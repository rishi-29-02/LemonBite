package com.rm.lemonbite.domain.repository

import com.rm.lemonbite.domain.models.MenuItem

interface MenuRepository {

    suspend fun getMenu() : Result<List<MenuItem>>

    suspend fun getMenuById(id: Int) : Result<MenuItem>
}