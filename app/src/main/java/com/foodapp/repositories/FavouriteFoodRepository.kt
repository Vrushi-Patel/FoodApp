package com.foodapp.repositories

import com.foodapp.models.Food
import com.foodapp.room.database.AppDatabase
import com.foodapp.room.relations.FoodIngredientRelation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface FavouriteFoodRepository {

    fun getFavouriteFood(db: AppDatabase): Flow<List<FoodIngredientRelation>>

    fun createFavouriteFood(repositoryFood: FoodRepository, db: AppDatabase, food: Food)

    fun deleteFavouriteFood(
        repositoryFood: FoodRepository,
        db: AppDatabase,
        favouriteFood: FoodIngredientRelation
    )

    fun updateFavouriteFood(
        repositoryFood: FoodRepository,
        db: AppDatabase,
        foodComposite: Food,
        food: FoodIngredientRelation
    )
}

class FavouriteFoodRepositoryImpl : FavouriteFoodRepository {

    override fun getFavouriteFood(db: AppDatabase): Flow<List<FoodIngredientRelation>> {
        return db.favouriteDao().getAllFavourites()
    }

    override fun createFavouriteFood(repositoryFood: FoodRepository, db: AppDatabase, food: Food) {
        repositoryFood.insertFoodData(db, food, isFavorite = true)
    }

    override fun deleteFavouriteFood(
        repositoryFood: FoodRepository,
        db: AppDatabase,
        favouriteFood: FoodIngredientRelation
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            repositoryFood.deleteFood(db, favouriteFood)
            db.favouriteDao().deleteFood(favouriteFood.food.foodId!!)
        }
    }

    override fun updateFavouriteFood(
        repositoryFood: FoodRepository,
        db: AppDatabase,
        foodComposite: Food,
        food: FoodIngredientRelation
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            repositoryFood.deleteFood(db, food)
            createFavouriteFood(repositoryFood, db, foodComposite)
        }
    }
}