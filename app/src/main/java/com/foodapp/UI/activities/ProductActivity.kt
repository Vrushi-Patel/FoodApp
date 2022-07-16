package com.foodapp.UI.activities

import FoodFactoryImpl
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.UI.adapters.CategoryAdapter
import com.foodapp.UI.adapters.MoreAdapter
import com.foodapp.UI.common.setBottomNavbar
import com.foodapp.UI.common.setTopNavbar
import com.foodapp.builder.FoodBuilderImpl

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        setBottomNavbar(this)
        setTopNavbar(this)

        val categoryList = findViewById<RecyclerView>(R.id.categoryList)
        val moreList = findViewById<RecyclerView>(R.id.moreList)

        val builder = FoodBuilderImpl(FoodFactoryImpl())
        categoryList.apply {
            layoutManager = LinearLayoutManager(baseContext)
            (layoutManager as LinearLayoutManager).orientation = RecyclerView.HORIZONTAL
            adapter =
                CategoryAdapter(
                    mutableListOf(
                        builder.makeVegBurger(),
                        builder.makeCoke(),
                        builder.makeNonVegBurger(),
                        builder.makeFries(),
                        builder.makeIceCream(),
                    )
                )
        }
        moreList.apply {
            layoutManager = LinearLayoutManager(baseContext)
            (layoutManager as LinearLayoutManager).orientation = RecyclerView.HORIZONTAL
            adapter =
                MoreAdapter(
                    mutableListOf(
                        builder.makeBurgerCombo(),
                        builder.makeNonVegBurgerMeal(),
                        builder.makeVegBurgerMeal(),
                    )
                )
        }
    }


}