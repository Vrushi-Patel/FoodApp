package com.foodapp.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.foodapp.room.entities.Food

@Dao
interface CartDao {
    @Query("SELECT * FROM cartList")
    fun getAll(): List<Food>

    @Query("SELECT * FROM cartList WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Food>

    @Query("SELECT * FROM cartList WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Food

    @Insert
    fun insertAll(vararg ingredient: Food)

    @Delete
    fun delete(ingredient: Food)
}