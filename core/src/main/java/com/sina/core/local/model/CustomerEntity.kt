package com.sina.core.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey
    val id: Int,
    val email: String,
    @ColumnInfo(defaultValue = "")
    val username: String,
    @ColumnInfo(name = "first_name", defaultValue = "")
    val firstName: String,
    @ColumnInfo(name = "last_name", defaultValue = "")
    val lastName: String,
    @ColumnInfo(name = "avatar_url", defaultValue = "")
    val avatarUrl: String,
    val role: String,
)