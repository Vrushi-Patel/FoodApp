package com.foodapp.UI.activities

import OperationType
import UserOperations
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.R
import com.foodapp.UI.common.setBottomNavbar
import com.foodapp.UI.common.setIngredientPage
import com.foodapp.UI.common.setProductPage
import com.foodapp.UI.common.setTopNavbar
import com.foodapp.databinding.ActivityHomeBinding
import com.foodapp.models.Food
import com.foodapp.models.Ingredient
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    companion object {
        lateinit var food: Food
        fun setData(foodData: Food) = foodData.also { food = it }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopNavbar(this)
        setBottomNavbar(this)

        binding.orderButton.setOnClickListener {
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
                    val intent = Intent(this, CartActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            alertDialog.create()
            alertDialog.show()
        }
        Picasso.with(baseContext)
            .load(food.url)
            .placeholder(R.drawable.burger)
            .into(binding.collapsableMenu.image)

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