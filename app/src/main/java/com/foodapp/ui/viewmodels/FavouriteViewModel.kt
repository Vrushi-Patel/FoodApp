package com.foodapp.ui.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.foodapp.repositories.FavouriteFoodRepositoryImpl
import com.foodapp.repositories.FoodRepositoryImpl
import com.foodapp.room.database.AppDatabase
import com.foodapp.room.relations.FoodIngredientRelation
import com.foodapp.ui.activities.UpdateFavouriteProductActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repositoryFavouriteFood: FavouriteFoodRepositoryImpl,
    val repositoryFood: FoodRepositoryImpl,
    val db: AppDatabase,
) : ViewModel() {

    fun delete(food: FoodIngredientRelation) {
        repositoryFavouriteFood.deleteFavouriteFood(repositoryFood, db, food)
    }

    fun update(food: FoodIngredientRelation, context: Context) {
        UpdateFavouriteProductActivity.food = food
        val updateProductActivity = UpdateFavouriteProductActivity()
        val intent = Intent(context, updateProductActivity::class.java)
        context.startActivity(intent)
    }

    val favouriteProducts = repositoryFavouriteFood.getFavouriteFood(db)
}