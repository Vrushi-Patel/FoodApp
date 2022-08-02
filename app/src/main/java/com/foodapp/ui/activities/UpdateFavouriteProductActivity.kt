package com.foodapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.R
import com.foodapp.ui.common.setTopNavbar
import com.foodapp.ui.fragments.AddIngredientFragment
import com.foodapp.ui.fragments.AddProductFragment
import com.foodapp.databinding.ActivityUpdateFavouriteProductBinding
import com.foodapp.room.relations.FoodIngredientRelation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFavouriteProductActivity : AppCompatActivity() {

    private var _biding: ActivityUpdateFavouriteProductBinding? = null
    private val binding get() = _biding!!

    companion object {
        lateinit var food: FoodIngredientRelation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _biding = ActivityUpdateFavouriteProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopNavbar(this)

        updateCombo()
        food.ingredients?.let {
            if (it.isNotEmpty()) {
                updateBurger()
            }
        }
    }

    private fun updateCombo() {
        val fragment = AddProductFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameCreateProduct, fragment)
            .addToBackStack(null)
            .commit()
        fragment.setFoodData(food)
        binding.updateFavBtn.setOnClickListener { fragment.onUpdate(food) }
    }

    private fun updateBurger() {
        val fragment = AddIngredientFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameCreateProduct, fragment)
            .addToBackStack(null)
            .commit()
        fragment.setFoodData(food)
        binding.updateFavBtn.setOnClickListener { fragment.onUpdate(food) }
    }
}