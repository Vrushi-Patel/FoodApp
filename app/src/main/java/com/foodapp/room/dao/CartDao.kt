package com.foodapp.room.dao

import androidx.room.*
import com.foodapp.room.entities.Cart
import com.foodapp.room.relations.CartFoodRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Transaction
    @Query("SELECT * FROM cartList")
    fun getAll(): Flow<List<CartFoodRelation>>

    @Transaction
    @Query("SELECT * FROM cartList WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flow<List<CartFoodRelation>>

    @Transaction
    @Query("SELECT * FROM cartList WHERE cartFoodId LIKE :foodId LIMIT 1")
    fun findByFoodId(foodId: Int): CartFoodRelation

    @Insert
    fun insertCartData(vararg cartItem: Cart)

    @Update
    fun updateCartItem(vararg cartItem: Cart)

    @Delete
    fun delete(cartItem: Cart)

    @Query("SELECT COUNT(cartFoodId) FROM cartList WHERE cartFoodId is :foodId")
    fun isInCart(foodId: Int): Int

    @Query("SELECT id FROM cartList ORDER BY id DESC LIMIT 1")
    fun lastEntryId(): Int
}