package com.foodapp.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Product(
    @ColumnInfo var calories: Double,
    @ColumnInfo var price: Double,
    @ColumnInfo var name: String,
    @ColumnInfo var url: String,
)