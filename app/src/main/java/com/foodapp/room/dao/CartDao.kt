package com.foodapp.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.foodapp.room.entities.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cartList")
    fun getAll(): Flow<List<Cart>>

    @Query("SELECT * FROM cartList WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flow<List<Cart>>

    @Query("SELECT * FROM cartList WHERE foodId LIKE :foodId LIMIT 1")
    fun findByFoodId(foodId: Int): Cart

    @Insert
    fun insertAll(vararg ingredient: Cart)

    @Delete
    fun delete(ingredient: Cart)
}