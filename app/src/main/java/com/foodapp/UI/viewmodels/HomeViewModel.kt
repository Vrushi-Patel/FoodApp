package com.foodapp.UI.viewmodels

import androidx.lifecycle.ViewModel
import com.foodapp.AppClass
import com.foodapp.room.relations.FoodIngredientRelation
import kotlinx.coroutines.flow.Flow

class HomeViewModel : ViewModel() {

    var subProducts: Flow<List<FoodIngredientRelation>> = app.repositoryFood.getAllSubProducts(
        app,
        food.food.foodId!!
    )

    companion object {
        lateinit var food: FoodIngredientRelation
        lateinit var app: AppClass
    }
}