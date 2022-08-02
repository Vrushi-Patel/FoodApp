package com.foodapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.foodapp.AppClass
import com.foodapp.repositories.CartRepositoryImpl
import com.foodapp.repositories.FoodRepositoryImpl
import com.foodapp.room.database.AppDatabase
import com.foodapp.room.relations.FoodIngredientRelation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryCart: CartRepositoryImpl,
    val repositoryFood: FoodRepositoryImpl,
    val db: AppDatabase,
) : ViewModel() {

    fun addToCart(food: FoodIngredientRelation?) {
        repositoryCart.addToCart(db, food!!.food)
    }

    var subProducts: Flow<List<FoodIngredientRelation>?> = repositoryFood.getAllSubProducts(
        db,
        food.food.foodId!!
    )

    companion object {
        lateinit var food: FoodIngredientRelation
    }
}