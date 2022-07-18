package com.foodapp.UI.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.UI.adapters.CartAdapter
import com.foodapp.UI.common.setBottomNavbar
import com.foodapp.UI.common.setTopNavbar
import com.foodapp.UI.viewmodels.CartViewModel
import com.foodapp.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_cart)
        setTopNavbar(this)
        setBottomNavbar(this)

        val cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
//        val cartList = findViewById<RecyclerView>(R.id.cartItems)

        cartViewModel.cartItems.observe(this) { cartItems ->
            binding.cartItems.apply {
                layoutManager = LinearLayoutManager(baseContext)
                (layoutManager as LinearLayoutManager).orientation = RecyclerView.VERTICAL
                adapter = CartAdapter(cartItems, cartViewModel.userOperation)
            }
        }

//        val builder = FoodBuilderImpl(FoodFactoryImpl())
//        val userOperation = UserOperations()
//        userOperation.performOperation(OperationType.AddToCart, builder.makeBurgerCombo(), null)
//        userOperation.performOperation(OperationType.AddToCart, builder.makeVegBurger(), null)
//        userOperation.performOperation(OperationType.AddToCart, builder.makeIceCream(), null)
//        userOperation.cartItems


//        val cartList = findViewById<RecyclerView>(R.id.cartItems)
//        cartList.apply {
//            layoutManager = LinearLayoutManager(baseContext)
//            (layoutManager as LinearLayoutManager).orientation = RecyclerView.VERTICAL
//            adapter =
//                CartAdapter(
//                    userOperation.cartItems, userOperation
//                )
//        }
    }
}