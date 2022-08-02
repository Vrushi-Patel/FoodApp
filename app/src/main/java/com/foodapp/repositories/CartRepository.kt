package com.foodapp.repositories

import com.foodapp.room.database.AppDatabase
import com.foodapp.room.entities.Cart
import com.foodapp.room.entities.Food
import com.foodapp.room.relations.CartFoodRelation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface CartRepository {

    fun getCartList(db: AppDatabase): Flow<List<CartFoodRelation>>

    fun addToCart(db: AppDatabase, food: Food)

    fun removeFromCart(db: AppDatabase, foodId: Int)
}

class CartRepositoryImpl @Inject constructor() : CartRepository {

    override fun getCartList(db: AppDatabase): Flow<List<CartFoodRelation>> {
        return db.cartDao().getAll()
    }

    override fun addToCart(db: AppDatabase, food: Food) {
        CoroutineScope(Dispatchers.Default).launch {
            when (db.cartDao().isInCart(foodId = food.foodId ?: 0)) {
                0 -> {
                    db.cartDao()
                        .insertCartData(Cart(id = null, cartFoodId = food.foodId!!, quantity = 1))
                }
                else -> {
                    val cartData = getCartItemFromFoodId(db, food.foodId!!)
                    cartData.cartData.quantity = cartData.cartData.quantity + 1
                    db.cartDao().updateCartItem(cartData.cartData)
                }
            }
        }
    }

    override fun removeFromCart(db: AppDatabase, foodId: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            val data = getCartItemFromFoodId(db, foodId).cartData
            when (data.quantity) {
                1 -> {
                    db.cartDao().delete(data)
                }
                else -> {
                    data.quantity = data.quantity - 1
                    db.cartDao()
                        .updateCartItem(data)
                }
            }
        }
    }

    private fun getCartItemFromFoodId(db: AppDatabase, foodId: Int): CartFoodRelation {
        return db.cartDao().findByFoodId(foodId = foodId)
    }
}