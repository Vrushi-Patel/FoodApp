package com.foodapp.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartList")
data class Cart(
    @PrimaryKey val id: Int,
    @ColumnInfo var food: Food,
)