package com.foodapp.UI.activities

import OperationType
import UserOperations
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.foodapp.R
import com.foodapp.UI.common.setBottomNavbar
import com.foodapp.UI.common.setIngredientPage
import com.foodapp.UI.common.setProductPage
import com.foodapp.UI.common.setTopNavbar
import com.foodapp.models.Food
import com.foodapp.models.Ingredient
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() {

    companion object {
        lateinit var food: Food
        fun setData(foodData: Food) = foodData.also { food = it }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setTopNavbar(this)
        setBottomNavbar(this)
        val image = findViewById<ImageView>(R.id.image)
        val orderButton = findViewById<CardView>(R.id.orderButton)
        orderButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setMessage(R.string.add_to_cart_msg).setCancelable(true)
                .setNegativeButton(
                    R.string.no
                ) { _, i -> finish() }
                .setPositiveButton(
                    R.string.yes
                ) { _, i ->

                    UserOperations().performOperation(
                        OperationType.AddToCart,
                        food,
                        null
                    )
                    finish()

                }
            alertDialog.create()
            alertDialog.show()
        }
        Picasso.with(baseContext)
            .load(food.url)
            .placeholder(R.drawable.burger)
            .into(image)

        when (food) {
            is Ingredient -> {
                if (food.ingredients.isEmpty()) {
                    setIngredientPage(this, food)
                } else {
                    setProductPage(this, food)
                }
            }
            else -> {
                setProductPage(this, food)
            }
        }


    }


}