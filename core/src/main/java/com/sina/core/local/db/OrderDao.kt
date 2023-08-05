package com.sina.core.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sina.core.local.model.OrderEntity

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrderLocal(orderEntity: OrderEntity)

    @Delete
    suspend fun deleteOrderLocal(orderEntity: OrderEntity)

    @Update
    suspend fun updateOrderLocal(orderEntity: OrderEntity)

    @Query("select * from `order`")
    fun getListOrderLocal(): List<OrderEntity>
}