package com.foodapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    var isFood = true
    val isFoodObserver: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun setData(isFood: Boolean, food: FoodIngredientRelation?) {
        this.isFood = isFood
        isFoodObserver.value = isFood
        food?.let {
            HomeViewModel.food = it
            subProducts = repositoryFood.getAllSubProducts(
                db,
                Companion.food.food.foodId!!
            )
        }
    }

    fun addToCart(food: FoodIngredientRelation?) {
        repositoryCart.addToCart(db, food!!.food)
    }

    lateinit var subProducts: Flow<List<FoodIngredientRelation>?>

    companion object {
        lateinit var food: FoodIngredientRelation
    }
}