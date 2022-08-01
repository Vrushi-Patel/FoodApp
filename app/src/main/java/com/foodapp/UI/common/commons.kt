package com.foodapp.UI.common

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.UI.activities.*
import com.foodapp.UI.adapters.ProductIngredientAdapter
import com.foodapp.UI.adapters.ProductSubProductAdapter
import com.foodapp.room.entities.Ingredient
import com.foodapp.room.relations.FoodIngredientRelation

fun setBottomNavbar(activity: Activity) {
    val homeIcon = activity.findViewById<ImageButton>(R.id.home)
    val cartIcon = activity.findViewById<ImageButton>(R.id.cart)
    val favouriteIcon = activity.findViewById<ImageButton>(R.id.favourite)
    val profileIcon = activity.findViewById<ImageButton>(R.id.profile)
    val addProductIcon = activity.findViewById<ImageButton>(R.id.createProduct)

    addProductIcon.setOnClickListener {
        activity.startActivity(
            Intent(
                activity,
                CreateProductActivity::class.java
            )
        )
    }
    homeIcon.setOnClickListener {
        if (activity !is HomeActivity) {
            activity.startActivity(Intent(activity, HomeActivity::class.java))
            activity.finish()
        }
    }
    favouriteIcon.setOnClickListener {
        if (activity !is FavouriteFoodActivity) {
            activity.startActivity(Intent(activity, FavouriteFoodActivity::class.java))
            activity.finish()
        }
    }
    cartIcon.setOnClickListener {
        if (activity !is CartActivity) {
            activity.startActivity(Intent(activity, CartActivity::class.java))
            activity.finish()
        }
    }
    when (activity) {
        is CartActivity -> {
            cartIcon.setColorFilter(ContextCompat.getColor(activity, R.color.colorOrange))
        }
        is FavouriteFoodActivity -> {
            favouriteIcon.setColorFilter(ContextCompat.getColor(activity, R.color.colorOrange))
        }
        else -> {
            homeIcon.setColorFilter(ContextCompat.getColor(activity, R.color.colorOrange))
        }
    }
}

fun setProductPage(
    activity: ProductActivity,
    food: FoodIngredientRelation,
    subProducts: List<FoodIngredientRelation>
) {
    val ingredientPage = activity.findViewById<View>(R.id.ingredientPage)
    val productPage = activity.findViewById<View>(R.id.productPage)
    ingredientPage.visibility = View.GONE
    productPage.visibility = View.VISIBLE
    val ingredientsList = activity.findViewById<RecyclerView>(R.id.ingredientsList)
    ingredientsList.apply {
        layoutManager = LinearLayoutManager(activity)
        (layoutManager as LinearLayoutManager).orientation = RecyclerView.VERTICAL
        adapter = ProductIngredientAdapter(food.ingredients!!)
    }
    val productsList = activity.findViewById<RecyclerView>(R.id.productsList)
    productsList.apply {
        layoutManager = LinearLayoutManager(activity)
        (layoutManager as LinearLayoutManager).orientation = RecyclerView.VERTICAL
        adapter =
            ProductSubProductAdapter(subProducts)
    }
}

fun setTopNavbar(activity: Activity) {

    val back = activity.findViewById<ImageView>(R.id.back)
    val menu = activity.findViewById<ImageView>(R.id.menu)
    back.setOnClickListener { activity.finish() }
    menu.setOnClickListener { }
}

fun setIngredientPage(activity: ProductActivity, food: Ingredient) {
    val ingredientPage = activity.findViewById<View>(R.id.ingredientPage)
    val productPage = activity.findViewById<View>(R.id.productPage)
    ingredientPage.visibility = View.VISIBLE
    productPage.visibility = View.GONE

    val price = activity.findViewById<TextView>(R.id.price)
    val name = activity.findViewById<TextView>(R.id.name)
    val data = activity.findViewById<TextView>(R.id.data)

    price.text = "₹ " + food.product.price.toString()
    name.text = food.product.name
    data.text = food.toString()
}