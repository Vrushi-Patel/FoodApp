package com.foodapp.UI.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.foodapp.AppClass
import com.foodapp.UI.activities.UpdateFavouriteProductActivity
import com.foodapp.room.relations.FoodIngredientRelation

class FavouriteViewModel : ViewModel() {

    fun delete(food: FoodIngredientRelation) {
        app.repositoryFavouriteFood.deleteFavouriteFood(app.repositoryFood, app.db, food)
    }

    fun update(food: FoodIngredientRelation, context: Context) {
        UpdateFavouriteProductActivity.food = food
        val updateProductActivity = UpdateFavouriteProductActivity()
        val intent = Intent(context, updateProductActivity::class.java)
        context.startActivity(intent)
    }

    val favouriteProducts = app.repositoryFavouriteFood.getFavouriteFood(app.db)

    companion object {
        lateinit var app: AppClass
    }
}