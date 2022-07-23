package com.foodapp.UI.viewmodels

import androidx.lifecycle.ViewModel
import com.foodapp.AppClass
import com.foodapp.room.entities.Ingredient
import com.foodapp.room.relations.FoodIngredientRelation
import kotlinx.coroutines.flow.Flow

class ProductViewModel : ViewModel() {
    var ingredientList: Flow<List<Ingredient>> = app.repositoryFood.getAllIngredients(app)
    var productList: Flow<List<FoodIngredientRelation>> = app.repositoryFood.getAllProducts(app)

    companion object {
        lateinit var app: AppClass
    }
}