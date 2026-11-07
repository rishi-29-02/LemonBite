package com.rm.lemonbite.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.rm.lemonbite.data.mapper.toDomain
import com.rm.lemonbite.data.remote.model.MenuItemDto
import com.rm.lemonbite.domain.models.MenuItem
import com.rm.lemonbite.domain.repository.MenuRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepositoryImpl @Inject constructor(
    private val firestoreDB: FirebaseFirestore
) : MenuRepository {
    override suspend fun getMenu() : Result<List<MenuItem>> {
        val query = firestoreDB.collection("menu")
        try {
            val items = query
                .get()
                .await()
                .toObjects(MenuItemDto::class.java)
            Log.d("menu", items.toString())
            return Result.success(items.map { it.toDomain() })
        } catch (e:Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getMenuById(id: Int) : Result<MenuItem> {
        val query = firestoreDB.collection("menu").whereEqualTo("id", id).limit(1)
        try {
            val item = query.get(Source.CACHE).await()
                .toObjects(MenuItemDto::class.java)
                .firstOrNull()
                ?: query.get().await()
                    .toObjects(MenuItemDto::class.java)
                    .firstOrNull()
            return if (item != null) {
                Result.success(item.toDomain())
            } else {
                Result.failure(NoSuchElementException("Menu item with id $id not found"))
            }
        } catch (e:Exception) {
            return Result.failure(e)
        }
    }
}