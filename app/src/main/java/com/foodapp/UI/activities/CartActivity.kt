package com.foodapp.UI.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.UI.adapters.CartAdapter
import com.foodapp.UI.common.setBottomNavbar
import com.foodapp.UI.common.setTopNavbar
import food_app_assignment.builder.ProductComboMealBuilderImpl
import food_app_assignment.command.OperationType
import food_app_assignment.command.UserOperations
import food_app_assignment.factory.FoodFactoryImpl

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setTopNavbar(this)
        setBottomNavbar(this)

        val builder = ProductComboMealBuilderImpl(FoodFactoryImpl())
        val userOperation = UserOperations()
        userOperation.performOperation(OperationType.AddToCart, builder.makeBurgerCombo(), null)
        userOperation.performOperation(OperationType.AddToCart, builder.makeVegBurger(), null)
        userOperation.performOperation(OperationType.AddToCart, builder.makeIceCream(), null)
        userOperation.cartItems
        val cartList = findViewById<RecyclerView>(R.id.cartItems)
        cartList.apply {
            layoutManager = LinearLayoutManager(baseContext)
            (layoutManager as LinearLayoutManager).orientation = RecyclerView.VERTICAL
            adapter =
                CartAdapter(
                    userOperation.cartItems, userOperation
                )
        }
    }
}