package com.foodapp.repositories

import android.app.Application
import com.foodapp.AppClass
import com.foodapp.room.dao.CartDao
import com.foodapp.room.database.AppDatabase
import com.foodapp.room.entities.Cart
import com.foodapp.room.entities.Food
import com.foodapp.room.entities.Ingredient
import com.foodapp.room.references.FoodIngredientRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.foodapp.models.Food as CompositeFood

class FoodRepository {

    fun getCartList(cartDao: CartDao): Flow<List<Cart>> {
        return cartDao.getAll()
    }

    fun getFoodData(app: Application) {
        CoroutineScope(Dispatchers.Default).launch {
            val db = (app as AppClass).db
            println(db.foodDao().getAll())
        }
    }

    fun insertFoodData(app: Application, food: CompositeFood) {
        CoroutineScope(Dispatchers.Default).launch {
            val db = (app as AppClass).db
            insertFood(food, db, null)
        }
    }

    private fun insertFood(food: CompositeFood, db: AppDatabase, parentId: Int?) {
        if (food.ingredients.isEmpty()) {
            insertIngredientEntity(food, db, parentId)
        } else {
            val foodNode = insertFoodEntity(food, db, parentId)
            food.ingredients.forEach {
                insertFood(it, db, foodNode.foodId)
            }
        }
    }

    private fun insertFoodEntity(food: CompositeFood, db: AppDatabase, parentId: Int?): Food {
        val foodData = Food(
            foodId = null,
            parentId = parentId,
            calories = food.calories,
            url = food.url,
            price = food.price,
            name = food.name
        )
        val cursor = db.foodDao().insertFood(foodData)
        foodData.foodId = db.foodDao().lastEntryFoodId()
        return foodData
    }

    private fun insertIngredientEntity(
        food: CompositeFood,
        db: AppDatabase,
        parentId: Int?
    ): Ingredient {
        val ingredientData = Ingredient(
            ingredientId = null,
            calories = food.calories,
            url = food.url,
            price = food.price,
            name = food.name
        )
        val cursor = db.foodDao().insertIngredient(ingredientData)
        ingredientData.ingredientId = db.foodDao().lastEntryIngredientId()
        parentId?.let { insertRef(db, parentId, ingredientData.ingredientId!!) }
        return ingredientData
    }

    private fun insertRef(db: AppDatabase, foodId: Int, ingredientId: Int) {
        val ref = FoodIngredientRef(ingredientId = ingredientId, foodId = foodId)
        db.foodDao().insertReference(ref)
    }
}
