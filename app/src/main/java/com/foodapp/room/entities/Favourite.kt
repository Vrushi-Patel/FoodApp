package com.foodapp.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouriteFood")
data class Favourite(
    @PrimaryKey var id: Int?,
    @ColumnInfo var foodId: Int
)