package com.rm.lemonbite.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.rm.lemonbite.data.local.room.LemonBiteDatabase
import com.rm.lemonbite.data.local.room.dao.CartDao
import com.rm.lemonbite.data.local.room.dao.OrderItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.datastore by preferencesDataStore(
    name = "lemonbite_datastore"
)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.datastore
    }

    @Provides
    @Singleton
    fun provideProvideFireBase(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): LemonBiteDatabase {
        return Room.databaseBuilder(
            context,
            LemonBiteDatabase::class.java,
            "lemonbite_database"
        ).build()
    }

    @Provides
    fun provideOrderItemDao(appDatabase: LemonBiteDatabase): OrderItemDao {
        return appDatabase.orderItemDao()
    }

    @Provides
    fun provideCartDao(appDatabase: LemonBiteDatabase): CartDao {
        return appDatabase.cartDao()
    }
}