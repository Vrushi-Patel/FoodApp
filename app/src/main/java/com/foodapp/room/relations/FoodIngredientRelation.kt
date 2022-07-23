package com.foodapp.room.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.foodapp.room.entities.Food
import com.foodapp.room.entities.Ingredient
import com.foodapp.room.references.FoodIngredientRef

data class FoodIngredientRelation(
    @Embedded val food: Food,
    @Relation(
        parentColumn = "foodId",
        entityColumn = "ingredientId",
        associateBy = Junction(FoodIngredientRef::class)
    )
    val ingredients: List<Ingredient>?,
)


