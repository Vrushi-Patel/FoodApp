package com.foodapp.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "foodItem")
data class Food(
    @PrimaryKey val id: Int,
    @ColumnInfo var calories: Double,
    @ColumnInfo var price: Double,
    @ColumnInfo var name: String,
    @ColumnInfo var url: String,
    @ColumnInfo var ingredients: MutableList<Food>,
)

