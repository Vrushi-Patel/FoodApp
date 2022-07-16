package com.foodapp.repositories

import com.foodapp.room.dao.CartDao
import com.foodapp.room.entities.Food

class FoodRepository {

    fun getCartList(cartDao: CartDao): List<Food> {
        return cartDao.getAll()
    }
}