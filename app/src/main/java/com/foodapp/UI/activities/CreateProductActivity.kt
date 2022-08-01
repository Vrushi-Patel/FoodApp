package com.foodapp.UI.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.R
import com.foodapp.UI.common.setTopNavbar
import com.foodapp.UI.fragments.AddIngredientFragment
import com.foodapp.UI.fragments.AddProductFragment
import com.foodapp.databinding.ActivityCreateProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProductActivity : AppCompatActivity() {

    private var _biding: ActivityCreateProductBinding? = null
    private val binding get() = _biding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _biding = ActivityCreateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopNavbar(this)

        selectCombo()
        binding.radioCreate.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.radioCombo -> {
                    selectCombo()
                }
                R.id.radioBurger -> {
                    selectBurger()
                }
            }
        }

    }

    private fun selectCombo() {
        binding.radioCombo.isChecked = true
        binding.radioBurger.isChecked = false
        val fragment = AddProductFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameCreateProduct, fragment)
            .addToBackStack(null)
            .commit()
        binding.addToFavBtn.setOnClickListener { fragment.onCreate() }
    }

    private fun selectBurger() {
        binding.radioCombo.isChecked = false
        binding.radioBurger.isChecked = true
        val fragment = AddIngredientFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameCreateProduct, fragment)
            .addToBackStack(null)
            .commit()
        binding.addToFavBtn.setOnClickListener { fragment.onCreate() }
    }
}