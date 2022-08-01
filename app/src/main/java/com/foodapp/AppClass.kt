package com.foodapp

import FoodFactoryImpl
import android.app.Application
import com.foodapp.builder.FoodBuilderImpl
import com.foodapp.repositories.CartRepositoryImpl
import com.foodapp.repositories.FavouriteFoodRepositoryImpl
import com.foodapp.repositories.FoodRepositoryImpl
import com.foodapp.room.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppClass : Application() {

    val db by lazy { AppDatabase.getDatabase(applicationContext) }
    val builder by lazy { FoodBuilderImpl(FoodFactoryImpl()) }
    val repositoryFood by lazy { FoodRepositoryImpl() }
    val repositoryFavouriteFood by lazy { FavouriteFoodRepositoryImpl() }
    val repositoryCart by lazy { CartRepositoryImpl() }
}