package com.sina.core.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sina.core.local.model.CustomerEntity
import com.sina.core.local.model.OrderEntity

@Database(
    entities = [
        OrderEntity::class,
        CustomerEntity::class
    ], version = 1, exportSchema = false
)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
    abstract fun customerDao(): CustomerDao
}