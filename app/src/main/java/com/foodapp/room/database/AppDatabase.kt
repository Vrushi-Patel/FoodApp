package com.foodapp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.foodapp.room.dao.CartDao
import com.foodapp.room.dao.FavouriteFoodDao
import com.foodapp.room.dao.FoodDao
import com.foodapp.room.entities.Cart
import com.foodapp.room.entities.Favourite
import com.foodapp.room.entities.Food
import com.foodapp.room.entities.Ingredient
import com.foodapp.room.references.FoodIngredientRef

@Database(
    entities = [Favourite::class, Food::class, Cart::class, Ingredient::class, FoodIngredientRef::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun foodDao(): FoodDao
    abstract fun favouriteDao(): FavouriteFoodDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}