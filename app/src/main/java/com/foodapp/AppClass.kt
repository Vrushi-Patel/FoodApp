package com.foodapp

import FoodFactoryImpl
import android.app.Application
import com.foodapp.builder.FoodBuilderImpl
import com.foodapp.repositories.CartRepository
import com.foodapp.repositories.FavouriteFoodRepository
import com.foodapp.repositories.FoodRepository
import com.foodapp.room.database.AppDatabase

class AppClass : Application() {

    val db by lazy { AppDatabase.getDatabase(applicationContext) }
    val builder by lazy { FoodBuilderImpl(FoodFactoryImpl()) }
    val repositoryFood by lazy { FoodRepository() }
    val repositoryFavouriteFood by lazy { FavouriteFoodRepository() }
    val repositoryCart by lazy { CartRepository() }
}