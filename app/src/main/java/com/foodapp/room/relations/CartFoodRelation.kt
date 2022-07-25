package com.foodapp.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.foodapp.room.entities.Cart
import com.foodapp.room.entities.Food

data class CartFoodRelation(
    @Embedded val cartData: Cart,
    @Relation(
        parentColumn = "cartFoodId",
        entityColumn = "foodId",
    )
    val food: Food,
)