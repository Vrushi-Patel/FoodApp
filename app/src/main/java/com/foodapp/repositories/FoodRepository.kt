package com.foodapp.repositories

import com.foodapp.AppClass
import com.foodapp.room.database.AppDatabase
import com.foodapp.room.entities.Favourite
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

    fun getAllProducts(db: AppDatabase): Flow<List<FoodIngredientRelation>> {
        return db.foodDao().getAllParents()
    }

    fun getAllProducts(db: AppDatabase, parentId: Int): Flow<List<FoodIngredientRelation>> {
        return db.foodDao().getAllParents(parentId)
    }

    fun getProductFromFoodId(db: AppDatabase, foodId: Int): Flow<FoodIngredientRelation> {
        return db.foodDao().getFoodFromFoodId(foodId)
    }

    fun deleteFood(db: AppDatabase, food: FoodIngredientRelation) {
        CoroutineScope(Dispatchers.Default).launch {

            db.foodDao().deleteFood(food.food)
            food.ingredients?.let {
                food.ingredients.forEach {
                    deleteFoodIngredientRef(
                        db,
                        FoodIngredientRef(
                            foodId = food.food.foodId!!,
                            ingredientId = it.ingredientId!!
                        )
                    )
                    deleteIngredient(db, it)
                }
            }

            getAllSubProducts(db, food.food.foodId!!).collect {
                it?.let {
                    it.forEach { i ->
                        deleteFood(db, i)
                    }
                }
            }
        }
    }

    private fun deleteIngredient(db: AppDatabase, ingredient: Ingredient) {
        db.foodDao().deleteIngredient(ingredient)
    }

    private fun deleteFoodIngredientRef(db: AppDatabase, foodIngredientRef: FoodIngredientRef) {
        db.foodDao().deleteFoodRef(foodIngredientRef)
    }

    fun getAllSubProducts(db: AppDatabase, parentId: Int): Flow<List<FoodIngredientRelation>?> {
        return db.foodDao().getAllSubProducts(parentId)
    }

    fun getAllIngredients(db: AppDatabase): Flow<List<Ingredient>> {
        return db.foodDao().getAllIngredients()
    }

    fun getAllIngredients(db: AppDatabase, parentId: Int): Flow<List<Ingredient>?> {
        return db.foodDao().getAllIngredients(parentId)
    }

    fun getAllIngredientsWithBasic(db: AppDatabase, parentId: Int): Flow<List<Ingredient>> {
        return db.foodDao().getAllIngredientsWithBasic(parentId)
    }

    fun insertFoodData(db: AppDatabase, food: CompositeFood, isFavorite: Boolean = false) {
        CoroutineScope(Dispatchers.Default).launch {
            delay(500)
            insertFood(food, db, null, isFavorite)
        }
    }

    private fun insertFood(
        food: CompositeFood,
        db: AppDatabase,
        parentId: Int?,
        isFavorite: Boolean = false
    ) {
        if (food.ingredients.isEmpty()) {
            insertIngredientEntity(food, db, parentId)
        } else {
            val foodNode = insertFoodEntity(food, db, parentId)
            if (isFavorite)
                db.favouriteDao().insertFavouriteFood(Favourite(null, foodNode.foodId!!))
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
                app.repositoryFood.insertFoodData(app.db, app.builder.makeAluPatty())
                delay(100)
                app.repositoryFood.insertFoodData(app.db, app.builder.makeCheeseSlice())
                delay(100)
                app.repositoryFood.insertFoodData(app.db, app.builder.makeChickenPatty())
                delay(100)
                app.repositoryFood.insertFoodData(app.db, app.builder.makeLettuce())
            }
            if (app.db.foodDao().getProductCount() == 0) {
                delay(100)
                app.repositoryFood.insertFoodData(app.db, app.builder.makeIceCream())
                delay(100)
                app.repositoryFood.insertFoodData(app.db, app.builder.makeFries())
                delay(100)
                app.repositoryFood.insertFoodData(app.db, app.builder.makeCoke())
                delay(100)
                app.repositoryFood.insertFoodData(app.db, app.builder.makeVegBurgerMeal())
                delay(100)
                app.repositoryFood.insertFoodData(app.db, app.builder.makeVegBurger())
                delay(100)
                app.repositoryFood.insertFoodData(app.db, app.builder.makeNonVegBurger())
                delay(100)
                app.repositoryFood.insertFoodData(app.db, app.builder.makeNonVegBurgerMeal())
                delay(100)
                app.repositoryFood.insertFoodData(app.db, app.builder.makeBurgerCombo())
            }
        }
    }
}
