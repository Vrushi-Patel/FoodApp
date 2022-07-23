package com.foodapp.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredientTable")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) var ingredientId: Int?,
    @Embedded var product: Product
)
