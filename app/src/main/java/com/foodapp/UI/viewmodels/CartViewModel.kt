package com.foodapp.UI.viewmodels

import FoodFactoryImpl
import OperationType
import UserOperations
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.builder.FoodBuilderImpl
import com.foodapp.models.Food

class CartViewModel : ViewModel() {
    val builder = FoodBuilderImpl(FoodFactoryImpl())
    val userOperation = UserOperations()


    private val _cartItems: MutableLiveData<MutableList<Food>> = MutableLiveData(mutableListOf())

    val cartItems: LiveData<MutableList<Food>>
        get() = _cartItems


    // Just Adding the Items to cart statically.
    // Afterword the items are going to be added dynamically
    init {
        userOperation.performOperation(OperationType.AddToCart, builder.makeBurgerCombo(), null)
        userOperation.performOperation(OperationType.AddToCart, builder.makeVegBurger(), null)
        userOperation.performOperation(OperationType.AddToCart, builder.makeIceCream(), null)
        cartItems.value?.addAll(userOperation.cartItems)
    }

}