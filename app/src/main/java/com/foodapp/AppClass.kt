package com.foodapp

import FoodFactoryImpl
import android.app.Application
import com.foodapp.builder.FoodBuilderImpl
import com.foodapp.room.database.AppDatabase

class AppClass : Application() {

    val db by lazy { AppDatabase.getDatabase(applicationContext) }
    val builder by lazy { FoodBuilderImpl(FoodFactoryImpl()) }
}