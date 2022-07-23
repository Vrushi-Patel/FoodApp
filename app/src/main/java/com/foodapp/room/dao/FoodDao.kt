package com.foodapp.room.dao

import androidx.room.*
import com.foodapp.room.entities.Food
import com.foodapp.room.entities.Ingredient
import com.foodapp.room.references.FoodIngredientRef
import com.foodapp.room.relations.FoodIngredientRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM foodItem WHERE foodId IN (:ids)")
    fun loadAllByIds(ids: IntArray): Flow<List<Food>>

    @Query("SELECT * FROM foodItem WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Food

    @Transaction
    @Query("SELECT * FROM foodItem")
    fun getAll(): List<FoodIngredientRelation>

    @Insert
    fun insertFood(vararg food: Food)

    @Insert
    fun insertIngredient(vararg ingredient: Ingredient)

    @Insert
    fun insertReference(vararg relation: FoodIngredientRef)

    @Delete
    fun delete(ingredient: Food)

    @Query("SELECT foodId FROM foodItem ORDER BY foodId DESC LIMIT 1")
    fun lastEntryFoodId(): Int

    @Query("SELECT ingredientId FROM ingredientTable ORDER BY ingredientId DESC LIMIT 1")
    fun lastEntryIngredientId(): Int
}