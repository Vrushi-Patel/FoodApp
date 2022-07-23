package com.foodapp.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredientTable")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) var ingredientId: Int?,
    @ColumnInfo var calories: Double,
    @ColumnInfo var price: Double,
    @ColumnInfo var name: String,
    @ColumnInfo var url: String,
)
