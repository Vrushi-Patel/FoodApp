package com.foodapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.foodapp.room.entities.Favourite
import com.foodapp.room.relations.FoodIngredientRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteFoodDao {

    @Transaction
    @Query("SELECT * FROM foodItem WHERE foodId IN (SELECT foodId FROM favouriteFood)")
    fun getAllFavourites(): Flow<List<FoodIngredientRelation>>

    @Insert
    fun insertFavouriteFood(vararg favouriteFood: Favourite)

    @Query("DELETE FROM favouriteFood WHERE foodId IS :favouriteFoodId")
    fun deleteFood(favouriteFoodId: Int)
}