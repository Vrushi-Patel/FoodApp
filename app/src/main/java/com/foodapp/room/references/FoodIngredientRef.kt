package com.foodapp.room.references

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "referenceTable", primaryKeys = ["foodId", "ingredientId"])
data class FoodIngredientRef(
    @ColumnInfo val foodId: Int,
    @ColumnInfo val ingredientId: Int,
)