package com.foodapp

import FoodFactoryImpl
import android.content.Context
import com.foodapp.builder.FoodBuilderImpl
import com.foodapp.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
}

@Module
@InstallIn(ActivityComponent::class, ViewModelComponent::class)
class ActivityModule {

    @Provides
    fun provideFoodFactory(): FoodBuilderImpl {
        return FoodBuilderImpl(FoodFactoryImpl())
    }
}
