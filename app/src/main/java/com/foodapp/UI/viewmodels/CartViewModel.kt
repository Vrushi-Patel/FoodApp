package com.foodapp.UI.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.AppClass
import com.foodapp.UI.activities.ProductActivity
import com.foodapp.room.relations.CartFoodRelation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    var cartItems: Flow<List<CartFoodRelation>> = app.repositoryCart.getCartList(app.db)

    companion object {
        lateinit var app: AppClass
    }

    fun addToCart(food: CartFoodRelation) {
        app.repositoryCart.addToCart(app.db, food.food)
    }

    fun removeFromCart(food: CartFoodRelation) {
        app.repositoryCart.removeFromCart(app.db, food.food.foodId!!)
    }

    fun openItemPage(food: CartFoodRelation, context: Context) {
        this.viewModelScope.launch {
            app.repositoryFood.getProductFromFoodId(app.db, foodId = food.food.foodId!!)
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