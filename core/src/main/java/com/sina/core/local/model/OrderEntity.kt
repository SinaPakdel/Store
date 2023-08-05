package com.sina.core.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["order_id"], tableName = "order")
data class OrderEntity(
    @ColumnInfo("order_id")
    val productId: Int,
    @ColumnInfo("order_quantity")
    val quantity: Int,
)