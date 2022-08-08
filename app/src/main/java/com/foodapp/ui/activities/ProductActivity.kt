package com.foodapp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.foodapp.R
import com.foodapp.databinding.ActivityProductBinding
import com.foodapp.room.entities.Ingredient
import com.foodapp.room.relations.FoodIngredientRelation
import com.foodapp.ui.common.setBottomNavbar
import com.foodapp.ui.common.setIngredientPage
import com.foodapp.ui.common.setProductPage
import com.foodapp.ui.common.setTopNavbar
import com.foodapp.ui.viewmodels.HomeViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        var ingredient: Ingredient? = null
        var food: FoodIngredientRelation? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopNavbar(this)
        setBottomNavbar(this)

        binding.productPage.orderButton.setOnClickListener {
            showAlertDialog({
                homeViewModel.addToCart(food)
                val intent = Intent(this, CartActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }, { finish() })
        }

        food?.let {
            HomeViewModel.food = food!!
            homeViewModel.setData(true, it)
        }

        ingredient?.let {
            homeViewModel.setData(false, null)
        }

        homeViewModel.isFoodObserver.observe({ this.lifecycle }, {
            if (it) {
                food?.let {
                    Picasso.with(baseContext)
                        .load(it.food.product.url)
                        .placeholder(R.drawable.burger)
                        .into(binding.collapsableMenu.image)
                    val activity = this
                    HomeViewModel.food = food!!
                    homeViewModel.viewModelScope.launch {
                        homeViewModel.subProducts.collect { list ->
                            list?.let { list1 ->
                                setProductPage(activity, it, list1, homeViewModel)
                            }
                        }
                    }
                }
            } else {
                ingredient?.let {
                    Picasso.with(baseContext)
                        .load(it.product.url)
                        .placeholder(R.drawable.burger)
                        .into(binding.collapsableMenu.image)
                    setIngredientPage(this, it)
                }
            }
        })
    }

    private fun showAlertDialog(yesFunction: () -> Unit, noFuction: () -> Unit) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage(R.string.add_to_cart_msg).setCancelable(true)
            .setNegativeButton(
                R.string.no
            ) { _, _ -> noFuction() }
            .setPositiveButton(
                R.string.yes
            ) { _, _ ->
                yesFunction()
            }
        alertDialog.create()
        alertDialog.show()
    }
}