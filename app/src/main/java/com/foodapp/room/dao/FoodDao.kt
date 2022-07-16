package com.foodapp.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.foodapp.models.Food

@Dao
interface FoodDao {
    @Query("SELECT * FROM foodItem")
    fun getAll(): List<Food>

    @Query("SELECT * FROM foodItem WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Food>

    @Query("SELECT * FROM foodItem WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Food

    @Insert
    fun insertAll(vararg ingredient: Food)

    @Delete
    fun delete(ingredient: Food)
}