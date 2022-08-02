package com.foodapp.ui.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.repositories.CartRepositoryImpl
import com.foodapp.repositories.FoodRepositoryImpl
import com.foodapp.room.database.AppDatabase
import com.foodapp.room.relations.CartFoodRelation
import com.foodapp.ui.activities.ProductActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repositoryCart: CartRepositoryImpl,
    val db: AppDatabase,
    val repositoryFood: FoodRepositoryImpl,
) : ViewModel() {

    var cartItems: Flow<List<CartFoodRelation>> = repositoryCart.getCartList(db)

    fun addToCart(food: CartFoodRelation) {
        repositoryCart.addToCart(db, food.food)
    }

    fun removeFromCart(food: CartFoodRelation) {
        repositoryCart.removeFromCart(db, food.food.foodId!!)
    }

    fun openItemPage(food: CartFoodRelation, context: Context) {
        this.viewModelScope.launch {
            repositoryFood.getProductFromFoodId(db, foodId = food.food.foodId!!)
                .collect {
                    val productActivity = ProductActivity()
                    ProductActivity.food = it
                    ProductActivity.ingredient = null
                    val intent = Intent(context, productActivity::class.java)
                    context.startActivity(intent)
                }
        }
    }
}