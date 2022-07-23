package com.foodapp.repositories

import android.app.Application
import com.foodapp.AppClass
import com.foodapp.room.dao.CartDao
import com.foodapp.room.database.AppDatabase
import com.foodapp.room.entities.Cart
import com.foodapp.room.entities.Food
import com.foodapp.room.entities.Ingredient
import com.foodapp.room.entities.Product
import com.foodapp.room.references.FoodIngredientRef
import com.foodapp.room.relations.FoodIngredientRelation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.foodapp.models.Food as CompositeFood

class FoodRepository {

    fun getCartList(cartDao: CartDao): Flow<List<Cart>> {
        return cartDao.getAll()
    }

    fun getAllProducts(app: Application): Flow<List<FoodIngredientRelation>> {
        val db = (app as AppClass).db
        return db.foodDao().getAllParents()
    }

    fun getAllSubProducts(app: Application, parentId: Int): Flow<List<FoodIngredientRelation>> {
        val db = (app as AppClass).db
        return db.foodDao().getAllSubProducts(parentId)
    }

    fun getAllIngredients(app: Application): Flow<List<Ingredient>> {
        val db = (app as AppClass).db
        return db.foodDao().getAllIngredients()
    }

    fun insertFoodData(app: Application, food: CompositeFood) {
        CoroutineScope(Dispatchers.Default).launch {
            val db = (app as AppClass).db
            delay(500)
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
            product = Product(
                calories = food.calories,
                url = food.url,
                price = food.price,
                name = food.name
            )
        )
        db.foodDao().insertFood(foodData)
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
            product = Product(
                calories = food.calories,
                url = food.url,
                price = food.price,
                name = food.name
            ),
        )
        db.foodDao().insertIngredient(ingredientData)
        ingredientData.ingredientId = db.foodDao().lastEntryIngredientId()
        parentId?.let { insertRef(db, parentId, ingredientData.ingredientId!!) }
        return ingredientData
    }

    private fun insertRef(db: AppDatabase, foodId: Int, ingredientId: Int) {
        val ref = FoodIngredientRef(ingredientId = ingredientId, foodId = foodId)
        db.foodDao().insertReference(ref)
    }

    fun insertBasicProducts(app: AppClass) {
        CoroutineScope(Dispatchers.Default).launch {
            if (app.db.foodDao().getIngredientsCount() == 0) {
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeAluPatty())
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeCoke())
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeCheeseSlice())
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeChickenPatty())
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeLettuce())
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeIceCream())
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeFries())
            }
            if (app.db.foodDao().getProductCount() == 0) {
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeVegBurgerMeal())
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeVegBurger())
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeNonVegBurger())
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeNonVegBurgerMeal())
                delay(100)
                app.repositoryFood.insertFoodData(app, app.builder.makeBurgerCombo())
            }
        }
    }
}
