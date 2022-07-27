package com.foodapp.repositories

import com.foodapp.AppClass
import com.foodapp.models.Food
import com.foodapp.room.database.AppDatabase
import com.foodapp.room.relations.FoodIngredientRelation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavouriteFoodRepository {

    fun getFavouriteFood(db: AppDatabase): Flow<List<FoodIngredientRelation>> {
        return db.favouriteDao().getAllFavourites()
    }

    fun createFavouriteFood(app: AppClass, food: Food) {
        app.repositoryFood.insertFoodData(app.db, food, isFavorite = true)
    }

    fun deleteFavouriteFood(app: AppClass, favouriteFood: FoodIngredientRelation) {
        CoroutineScope(Dispatchers.Default).launch {
            app.repositoryFood.deleteFood(app.db, favouriteFood)
            app.db.favouriteDao().deleteFood(favouriteFood.food.foodId!!)
        }
    }

    fun updateFavouriteFood(app: AppClass, foodComposite: Food, food: FoodIngredientRelation) {
        CoroutineScope(Dispatchers.Default).launch {
            app.repositoryFood.deleteFood(app.db, food)
            createFavouriteFood(app, foodComposite)
        }
    }
}