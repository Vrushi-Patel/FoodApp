package com.foodapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.foodapp.repositories.FoodRepositoryImpl
import com.foodapp.room.database.AppDatabase
import com.foodapp.room.entities.Ingredient
import com.foodapp.room.relations.FoodIngredientRelation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val repositoryFood: FoodRepositoryImpl,
    val db: AppDatabase,
) : ViewModel() {
    var ingredientList: Flow<List<Ingredient>> = repositoryFood.getAllIngredients(db)
    var productList: Flow<List<FoodIngredientRelation>> = repositoryFood.getAllProducts(db)
}