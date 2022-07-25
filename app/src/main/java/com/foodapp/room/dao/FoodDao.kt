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

    @Transaction
    @Query("SELECT * FROM foodItem WHERE parentId IS null")
    fun getAllParents(): Flow<List<FoodIngredientRelation>>

    @Transaction
    @Query("SELECT * FROM foodItem WHERE foodId IS :foodId")
    fun getFoodFromFoodId(foodId: Int): Flow<FoodIngredientRelation>

    @Query("SELECT * FROM ingredientTable WHERE ingredientId NOT IN (SELECT ingredientId FROM referenceTable) ")
    fun getAllIngredients(): Flow<List<Ingredient>>

    @Insert
    fun insertFood(vararg food: Food)

    @Insert
    fun insertIngredient(vararg ingredient: Ingredient)

    @Insert
    fun insertReference(vararg relation: FoodIngredientRef)

    @Delete
    fun deleteFood(food: Food)

    @Delete
    fun deleteIngredient(ingredient: Ingredient)

    @Delete
    fun deleteFoodRef(foodIngredientRef: FoodIngredientRef)

    @Update
    fun update(food: Food)

    @Query("SELECT foodId FROM foodItem ORDER BY foodId DESC LIMIT 1")
    fun lastEntryFoodId(): Int

    @Query("SELECT ingredientId FROM ingredientTable ORDER BY ingredientId DESC LIMIT 1")
    fun lastEntryIngredientId(): Int

    @Query("SELECT COUNT(ingredientId) FROM ingredientTable")
    fun getIngredientsCount(): Int

    @Query("SELECT COUNT(foodId) FROM foodItem WHERE parentId is null")
    fun getProductCount(): Int

    @Query("SELECT * FROM foodItem WHERE parentId is :parentId")
    fun getAllSubProducts(parentId: Int): Flow<List<FoodIngredientRelation>>
}