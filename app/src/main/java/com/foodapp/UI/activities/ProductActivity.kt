package com.foodapp.UI.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.UI.adapters.CategoryAdapter
import com.foodapp.UI.adapters.MoreAdapter
import com.foodapp.UI.common.setBottomNavbar
import com.foodapp.UI.common.setTopNavbar
import com.foodapp.UI.viewmodels.ProductViewModel
import com.foodapp.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavbar(this)
        setTopNavbar(this)

        val productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productViewModel.categoryList.observe(this) {
            binding.categoryList.apply {
                layoutManager = LinearLayoutManager(baseContext)
                (layoutManager as LinearLayoutManager).orientation = RecyclerView.HORIZONTAL
                adapter =
                    CategoryAdapter(it)
            }
        }

        productViewModel.moreList.observe(this) { moreList ->
            binding.moreList.apply {
                layoutManager = LinearLayoutManager(baseContext)
                (layoutManager as LinearLayoutManager).orientation = RecyclerView.HORIZONTAL
                adapter =
                    MoreAdapter(moreList)
            }
        }
    }
}